package pers.agitator.mybatis.builder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import pers.agitator.mybatis.config.MapperBean;
import pers.agitator.mybatis.constant.XmlConstant;
import pers.agitator.mybatis.session.Configuration;
import pers.agitator.mybatis.util.StrUtil;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private final Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(in);

        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        // 解析 select 标签
        parseSelectElement(rootElement, namespace);
        // 其他1.0先不搞
    }

    private void parseSelectElement(Element rootElement, String namespace) throws ClassNotFoundException {
        List<Node> list = rootElement.selectNodes("//select");

        for (Node node : list) {
            MapperBean mapper = new MapperBean();
            Element element = (Element) node;
            String id = element.attributeValue(XmlConstant.MAPPER_ID);
            mapper.setId(id);
            String paramType = element.attributeValue(XmlConstant.PARAM_TYPE);
            if (StrUtil.isNotBlank(paramType)) {
                mapper.setParamType(Class.forName(paramType));
            }
            String resultType = element.attributeValue(XmlConstant.RESULT_TYPE);
            if (StrUtil.isNotBlank(resultType)) {
                mapper.setResultType(Class.forName(resultType));
            }
            mapper.setSql(element.getTextTrim());
            String key = namespace + "." + id;
            configuration.getMapperMap().put(key, mapper);
        }
    }

}
