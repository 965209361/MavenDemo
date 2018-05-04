package com.zqt.services.servicesImpl;

import com.zqt.model.HeFeiBBS_Domain;
import com.zqt.services.HeFei_BBSServerce;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class HeFei_BBS implements HeFei_BBSServerce {
    @Autowired
    HeFei_BBSServerce heFei_bbsServerce;

    private static Properties PropKit = HeFei_BBS.prop("hefei/hefeibbs.properties");

    @Override
    public void hefei_BBs() {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        pool.execute(() -> {
            Document doc = null;
            Document docList = null;
            Elements elems = null;
            Elements elemsList = null;
            String listUrl = null;
            String title = null;
            String content = null;
            String fbsj = null;
            StringBuffer buffer = new StringBuffer("insert into hefei_bbs(listUrl,title,content,fbsj,comment,commentCount) values");
            String sql = null;
            try {
                doc = Jsoup.connect("http://bbs.hefei.cc/forum.php?mod=forumdisplay&fid=196&filter=author&orderby=dateline").get();
                elems = doc.select("tbody[id^=\"normalthread\"] .xst");
                List<String> listQuery = query();
                String listtoString = listQuery.toString();
                for (int k = 0; k < elems.size(); k++) {
                    listUrl = elems.get(k).attr("href");
                    title = elems.get(k).text();
                    if (listtoString.equals(title)) {
                        continue;
                    }
                    int nums = 0;
                    while (nums < 3) {
                        try {
                            docList = Jsoup.connect(listUrl).get();
                            break;
                        } catch (Exception e) {
                            nums++;
                        } finally {
                            while (nums == 3)
                                continue;
                        }
                    }
                    content = docList.select(".pcb").first().text();
                    fbsj = docList.select(".hm span:first-child").text().replace("发表于", "").replace("更新于", "");
                    elemsList = docList.select(".pcb");
                    List<String> list = new ArrayList<>();
                    if (fbsj == null || content == null || title == null) {
                        continue;
                    }
                    if (elemsList.size() == 1) {
                        if (k < elems.size() - 1) {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s),", listUrl, title, content, fbsj, "空", 0));
                        } else {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s);", listUrl, title, content, fbsj, "空", 0));
                        }
                    } else {
                        for (int i = 1; i < elemsList.size(); i++) {
                            list.add(i + " 楼:" + elemsList.get(i).text() + "\t||");
                        }
                        if (k < elems.size() - 1) {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s),", listUrl, title, content, fbsj, list.toString(), elemsList.size()));
                        } else {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s);", listUrl, title, content, fbsj, list.toString(), elemsList.size()));
                        }
                    }
                    list.clear();
                }
                String buffTo = buffer.toString();
                if (!buffTo.isEmpty()) {
                    buffTo.substring(buffTo.length() - 1, buffTo.length()).replaceAll("[,;]", ";");
                    int numInsert = insert(buffTo);
                    if (numInsert > 0) {
                        System.out.println(numInsert + "条数据存入成功");
                    } else {
                        System.out.println(numInsert + "条存入失败");
                    }
                }
                System.out.println("本次刷新结束!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(title + "IOE出错!!!!!!!!!!!!!!");
            } catch (NullPointerException e) {
                System.out.println(title + "NullPointer出错!!!!!!!!!!!!!!");
            }
        });
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HeFeiBBS_Domain> QueryNews(int page, int pageCount) {
        String sql = String.format("SELECT * FROM `hefei_bbs` LIMIT %d,%d;", page, pageCount);
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        List<HeFeiBBS_Domain> Domainlist = new ArrayList<>();
        try {
            prep = connection().prepareStatement(sql);
            resultSet = prep.executeQuery();
            while (resultSet.next()) {
                HeFeiBBS_Domain heFeiBBS_domain = new HeFeiBBS_Domain();
                heFeiBBS_domain.setId(resultSet.getInt("id"));
                heFeiBBS_domain.setListUrl(resultSet.getString("listUrl"));
                heFeiBBS_domain.setTitle(resultSet.getString("title"));
                heFeiBBS_domain.setContent(resultSet.getString("content"));
                heFeiBBS_domain.setCommentCount(resultSet.getInt("commentCount"));
                heFeiBBS_domain.setComment(resultSet.getString("comment"));
                heFeiBBS_domain.setFbsj(resultSet.getString("fbsj"));
                Domainlist.add(heFeiBBS_domain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Domainlist;
    }

    /**
     * 新增语句 编辑语句
     *
     * @param sql
     * @return
     */
    public int insert(String sql) {
        PreparedStatement prep = null;
        int num = 0;
        try {
            prep = connection().prepareStatement(sql);
            num = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 对数据库的id和网址进行查询
     *
     * @return
     */
    public List<String> query() {
        PreparedStatement prep = null;
        String sql = "SELECT title from hefei_bbs;";
        List<String> list = new ArrayList<>();
        try {
            prep = connection().prepareStatement(sql);
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 连接JDBC的Connection语句
     *
     * @return
     */
    public Connection connection() {
        Connection conn = null;
        try {
            Class.forName(PropKit.getProperty("driver"));
            conn = DriverManager.getConnection(PropKit.getProperty("url"), PropKit.getProperty("username"), PropKit.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 配置的连接
     */
    public static Properties prop(String name) {
        InputStream inputStream = HeFei_BBS.class.getClassLoader().getResourceAsStream(name);
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 查询总共有多少条数
     *
     * @return
     */
    @Override
    public Integer QueryPageCount() {
        PreparedStatement prep = null;
        String sql = "select count(*) from hefei_bbs;";
        int num = 0;
        try {
            prep = connection().prepareStatement(sql);
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                num = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }


}
