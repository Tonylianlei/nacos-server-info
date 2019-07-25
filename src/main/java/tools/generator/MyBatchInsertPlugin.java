package tools.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 创建人:连磊
 * 日期: 2019/7/25. 17:34
 * 描述：
 */
public class MyBatchInsertPlugin {

    public static void addBatchInsertMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        Method ibsmethod = new Method();
        ibsmethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType ibsreturnType = FullyQualifiedJavaType.getIntInstance();
        ibsmethod.setReturnType(ibsreturnType);
        ibsmethod.setName("insertBatchSelective");
        FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType paramListType;
        if (introspectedTable.getRules().generateBaseRecordClass()) {
            paramListType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        } else {
            if (!introspectedTable.getRules().generatePrimaryKeyClass()) {
                throw new RuntimeException(Messages.getString("RuntimeError.12"));
            }
            paramListType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
        }
        paramType.addTypeArgument(paramListType);
        ibsmethod.addParameter(new org.mybatis.generator.api.dom.java.Parameter(paramType, "records"));
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(ibsmethod);
    }

    public static void addBatchInsertSelectiveXml(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        String incrementField = introspectedTable.getTableConfiguration().getProperties().getProperty("incrementField");
        if (incrementField != null) {
            incrementField = incrementField.toUpperCase();
        }

        org.mybatis.generator.api.dom.xml.XmlElement javaPropertyAndDbType = new org.mybatis.generator.api.dom.xml.XmlElement("trim");
        javaPropertyAndDbType.addAttribute(new Attribute("prefix", " ("));
        javaPropertyAndDbType.addAttribute(new Attribute("suffix", ")"));
        javaPropertyAndDbType.addAttribute(new Attribute("suffixOverrides", ","));
        org.mybatis.generator.api.dom.xml.XmlElement insertBatchElement = new org.mybatis.generator.api.dom.xml.XmlElement("insert");
        insertBatchElement.addAttribute(new Attribute("id", "insertBatchSelective"));
        insertBatchElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        org.mybatis.generator.api.dom.xml.XmlElement trim1Element = new org.mybatis.generator.api.dom.xml.XmlElement("trim");
        trim1Element.addAttribute(new Attribute("prefix", "("));
        trim1Element.addAttribute(new Attribute("suffix", ")"));
        trim1Element.addAttribute(new Attribute("suffixOverrides", ","));
        Iterator var8 = columns.iterator();

        while(var8.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var8.next();
            String columnName = introspectedColumn.getActualColumnName();
            if (!columnName.toUpperCase().equals(incrementField)) {
                org.mybatis.generator.api.dom.xml.XmlElement iftest = new org.mybatis.generator.api.dom.xml.XmlElement("if");
                iftest.addAttribute(new Attribute("test", "list[0]." + introspectedColumn.getJavaProperty() + "!=null"));
                iftest.addElement(new TextElement(columnName + ","));
                trim1Element.addElement(iftest);
                org.mybatis.generator.api.dom.xml.XmlElement trimiftest = new org.mybatis.generator.api.dom.xml.XmlElement("if");
                trimiftest.addAttribute(new Attribute("test", "item." + introspectedColumn.getJavaProperty() + "!=null"));
                trimiftest.addElement(new TextElement("#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "},"));
                javaPropertyAndDbType.addElement(trimiftest);
            }
        }

        org.mybatis.generator.api.dom.xml.XmlElement foreachElement = new org.mybatis.generator.api.dom.xml.XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        insertBatchElement.addElement(new TextElement("insert into " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        insertBatchElement.addElement(trim1Element);
        insertBatchElement.addElement(new TextElement(" values "));
        foreachElement.addElement(javaPropertyAndDbType);
        insertBatchElement.addElement(foreachElement);
        document.getRootElement().addElement(insertBatchElement);
    }


    /**
     *开 发 者：连磊
     *开发时间：2019/3/26 15:12
     *方 法 名：addBatchSqlInsertMethod
     *传入参数：[interfaze, introspectedTable]
     *返 回 值：void
     *描    述：生成没有判断的sql对应的dao接口
     **/
    public static void addBatchSqlInsertMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        Method ibsmethod = new Method();
        ibsmethod.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType ibsreturnType = FullyQualifiedJavaType.getIntInstance();
        ibsmethod.setReturnType(ibsreturnType);
        ibsmethod.setName("insertBatchSql");
        FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType paramListType;
        if (introspectedTable.getRules().generateBaseRecordClass()) {
            paramListType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        } else {
            if (!introspectedTable.getRules().generatePrimaryKeyClass()) {
                throw new RuntimeException(Messages.getString("RuntimeError.12"));
            }

            paramListType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
        }

        paramType.addTypeArgument(paramListType);
        ibsmethod.addParameter(new org.mybatis.generator.api.dom.java.Parameter(paramType, "records"));
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(ibsmethod);
    }

    /**
     *开 发 者：连磊
     *开发时间：2019/3/26 15:12
     *方 法 名：addBatchInsertXml
     *传入参数：[document, introspectedTable]
     *返 回 值：void
     *描    述：生成没有判断条件的xml批量插入方法
     **/
    public static void addBatchInsertXml(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        String incrementField = introspectedTable.getTableConfiguration().getProperties().getProperty("incrementField");
        if (incrementField != null) {
            incrementField = incrementField.toUpperCase();
        }

        org.mybatis.generator.api.dom.xml.XmlElement javaPropertyAndDbType = new org.mybatis.generator.api.dom.xml.XmlElement("trim");
        javaPropertyAndDbType.addAttribute(new Attribute("prefix", " ("));
        javaPropertyAndDbType.addAttribute(new Attribute("suffix", ")"));
        javaPropertyAndDbType.addAttribute(new Attribute("suffixOverrides", ","));

        org.mybatis.generator.api.dom.xml.XmlElement insertBatchElement = new org.mybatis.generator.api.dom.xml.XmlElement("insert");
        insertBatchElement.addAttribute(new Attribute("id", "insertBatchSql"));
        insertBatchElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        org.mybatis.generator.api.dom.xml.XmlElement trim1Element = new org.mybatis.generator.api.dom.xml.XmlElement("trim");
        trim1Element.addAttribute(new Attribute("prefix", "("));
        trim1Element.addAttribute(new Attribute("suffix", ")"));
        trim1Element.addAttribute(new Attribute("suffixOverrides", ","));
        Iterator var8 = columns.iterator();

        while(var8.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var8.next();
            String columnName = introspectedColumn.getActualColumnName();
            if (!columnName.toUpperCase().equals(incrementField)) {
                if (var8.hasNext()){
                    trim1Element.addElement(new TextElement(columnName + ","));
                    javaPropertyAndDbType.addElement(new TextElement("#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "},"));
                }else {
                    trim1Element.addElement(new TextElement(columnName));
                    javaPropertyAndDbType.addElement(new TextElement("#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "}"));
                }
            }
        }

        org.mybatis.generator.api.dom.xml.XmlElement foreachElement = new org.mybatis.generator.api.dom.xml.XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(javaPropertyAndDbType);

        insertBatchElement.addElement(new TextElement("insert into " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        insertBatchElement.addElement(trim1Element);
        insertBatchElement.addElement(new TextElement(" values "));
        insertBatchElement.addElement(foreachElement);
        document.getRootElement().addElement(insertBatchElement);
    }
}
