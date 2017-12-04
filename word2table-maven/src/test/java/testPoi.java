import classmanager.ClassManager;


import dao.DictionaryDao;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;

import common.Constant;
import pojo.Dictionary;

public class testPoi implements Constant {
    @Test
    public void testPoi() {
        SqlSession session = ClassManager.getSession();
        DictionaryDao mapper = session.getMapper(DictionaryDao.class);
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(FILE_LOCATION, TEST_FILE_NAME));
//            WordExtractor wordExtractor = new WordExtractor(fileInputStream);
//            System.out.println(wordExtractor.getText());

            HWPFDocument document = new HWPFDocument(fileInputStream);
            Range range = document.getRange();
            TableIterator tableIterator = new TableIterator(range);
            Table table;
            TableRow row;
            TableCell cell;
            byte[] bytes = new byte[1];
            bytes[0] = 7;


            while (tableIterator.hasNext()) {
                table = tableIterator.next();
                int numRows = table.numRows();

                //三级表格测试
                ArrayList<String> parentKey = new ArrayList<>(2);

                for (int i = 2; i < numRows; i++) {
                    int nowLevel = 1;
                    boolean flag = false;
                    row = table.getRow(i);
                    int numCells = row.numCells();

                    for (int j = 0; j < numCells; j++) {
                        cell = row.getCell(j);
                        //统计当前行空格数 ，！=0是为了防止说明为空的情况被记录
                        if (!flag) {
                            if (cell.text().trim().equals("")) {
                                nowLevel++;
                            } else {//这里做出统计结果
                                System.out.println("当前行空格数 ： " + (nowLevel - 1));
                                flag = true;
                            }
                        } else {//开始记录

                            for(int temp = 0;temp<(nowLevel-1)/2 ;temp++){
                                parentKey.add(temp,row.getCell());
                            }



                        }

                        System.out.println(cell.text());
                    }


                }

            }


            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Test
    public void testBookMaker() {
        HWPFDocument document = ClassManager.getDocument();
        Bookmarks bookmarks = document.getBookmarks();
        int bookmarksCount = bookmarks.getBookmarksCount();
        System.out.println(bookmarksCount);


        for (int i = 0; i < bookmarksCount; i++) {
            System.out.print(bookmarks.getBookmark(i).getName());
            System.out.print(" " + bookmarks.getBookmark(i).getStart());
            System.out.print(" " + bookmarks.getBookmark(i).getEnd());
            System.out.println();
        }
    }

    @Test
    public void testRange() {
        HWPFDocument document = ClassManager.getDocument();
        Range range = document.getRange();
        int numParagraphs = range.numParagraphs();
        for (int i = 0; i < numParagraphs; i++) {
            System.out.println(range.getParagraph(i).text());
            System.out.println("//////////////////////////////////////////////////////////////////////////");
        }
    }

    @Test
    public void testRangeList() {
        HWPFDocument document = ClassManager.getDocument();
        Range range = document.getRange();

        System.out.println(new Range(0, 20, document).text());
    }


    public static void main(String[] args) {
        HWPFDocument document = ClassManager.getDocument();
        Range range = document.getRange();
        int numParagraphs = range.numParagraphs();
        int begin = 0;
        int end = 0;
        ArrayList<Range> ranges = new ArrayList<>();

        for (int i = 0; i < numParagraphs; i++) {

            Paragraph paragraph = range.getParagraph(i);

            TableRow row;
            TableCell cell;

            //段落不在表内，即该段落是标题
            if (!paragraph.isInTable() && (!paragraph.text().trim().equals(""))) {

                int a = 0;

                System.out.print(i + " ");
                System.out.println(paragraph.text());


                Table table = null;
                try {
                    //获取到一张表格
                    table = range.getTable(range.getParagraph(i + 1));
                    System.out.println(table.numRows());


                    int numRows = table.numRows();
//                    for (int j = 0; j < numRows; j++) {
//                        row = table.getRow(j);
//
//                        int numCells = row.numCells();
//                        for (int k = 0; k < numCells; k++) {
//                            System.out.println();
//                            cell = row.getCell(k);
//
//                            System.out.println(cell.text());
//                        }
//                    }
                    //1.先确定是几级的表格
                    int level = 0;
                    for (int j = 0; j < numRows; j++) {
                        row = table.getRow(j);


                        if (row.getCell(0).text().contains("代码")) {
                            int index = row.numCells();
                            for (int temp = 0; temp < index; temp++) {
                                if (row.getCell(temp).text().contains("代码")) level++;
                            }
                            System.out.println(level + "级表格");
                            break;
                        }
                    }
                    //测试通过
//                  if(level != 1) {
//                      System.out.print("三级分别为 ");
//                      for(int j= 0; j<level;j++){
//                          System.out.print(table.getRow(0).getCell(j).text() + " ");
//                      }
//                      System.out.println();
//                  }

                    //2.对表格数据做出处理

                    //如果表格表面等级不为1，则从第3行开始统计，否则从第二行开始
                    int start;

                    if (level == 1) {
                        start = 1;
                    } else {
                        start = 2;

                    }

                    for (int j = start; j < numRows; j++) {

                        row = table.getRow(j);

                        int numCells = row.numCells();

                        for (int k = 0; k < numCells; k++) {
                            System.out.println();
                            cell = row.getCell(k);
                            //空白行，即第大列别下的小列表
                            if (cell.text().trim().equals("")) {

                            }

                            System.out.println(cell.text());
                        }
                    }


//                    Dictionary dictionary = new Dictionary();
//                    dictionary.setDicLabel(paragraph.text());
//                    dictionary.setIsActive(DICTIONARY_IS_ACTIVE);
//                    dictionary.setUpdateTime(new Date());
//                    dictionary.setDicType(i);
//                    dictionary.setDirKey();
//                    dictionary.setDicValue();
//                    dictionary.setParentKey();
//                    dictionary.setDescription();
//                    dictionary.setSortIndex();


                } catch (Exception e) {

                }


            }
//            if(paragraph.text().contains(".")) System.out.println(paragraph.text());
        }

    }
}
