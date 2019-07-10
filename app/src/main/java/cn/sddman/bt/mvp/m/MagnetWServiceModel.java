package cn.sddman.bt.mvp.m;

import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import cn.sddman.bt.mvp.e.MagnetInfo;
import cn.sddman.bt.mvp.e.MagnetRule;
import cn.sddman.bt.mvp.e.MagnetSearchBean;

public interface MagnetWServiceModel {
    int transformPage(Integer page);
    List<MagnetInfo> parser(MagnetSearchBean bean) throws IOException, XPathExpressionException, ParserConfigurationException, XPatherException;
    List<MagnetInfo> parser(MagnetRule rule, String keyword,String sort, int page) throws IOException, XPathExpressionException, ParserConfigurationException, XPatherException;
    List<MagnetInfo> parser(String rootUrl, String url, String keyword,String sort, int page, String group, String magnet, String name, String size, String count,String hot) throws IOException, XPathExpressionException, ParserConfigurationException, XPatherException;
}
