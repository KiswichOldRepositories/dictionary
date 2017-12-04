package classmanager;

import common.Constant;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.hwpf.HWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//单实例管理器
public class ClassManager implements  Constant {
    //mybatis的sqlsession实例
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSession(){
        if(sqlSessionFactory == null){
            try {
                sqlSessionFactory= new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(Constant.MYBATIS_LOCATION));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory.openSession();
    }

    //poi的HWPFDocument实例
    private static HWPFDocument document;

    public static  HWPFDocument getDocument(){
        if(document == null){
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(FILE_LOCATION,FILE_NAME));
                document = new HWPFDocument(fileInputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return document;
    }

}
