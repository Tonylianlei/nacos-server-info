package tools.generator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.internal.util.messages.Messages;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 创建人:连磊
 * 日期: 2019/7/25. 16:57
 * 描述：主要用作取消get/set方法
 */
public class MyPluginAdapte extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     *开 发 者：连磊
     *开发时间：2019/7/25 17:17
     *方 法 名：sqlMapGenerated
     *传入参数：[sqlMap, introspectedTable]
     *返 回 值：boolean
     *描    述：处理xml文件中每生成一次追加信息一次
     **/
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap,false);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        MyBatchInsertPlugin.addBatchInsertMethod(interfaze, introspectedTable);
        MyBatchInsertPlugin.addBatchSqlInsertMethod(interfaze, introspectedTable);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        MyBatchInsertPlugin.addBatchInsertSelectiveXml(document, introspectedTable);
        MyBatchInsertPlugin.addBatchInsertXml(document, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
