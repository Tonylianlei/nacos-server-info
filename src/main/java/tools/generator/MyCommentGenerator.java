package tools.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
import java.util.Set;

/**
 * 创建人:连磊
 * 日期: 2019/7/25. 15:12
 * 描述：实体信息备注
 */
public class MyCommentGenerator implements CommentGenerator {
    @Override
    public void addConfigurationProperties(Properties properties) {
    }

    /**
     *开 发 者：连磊
     *开发时间：2019/7/25 17:18
     *方 法 名：addFieldComment
     *传入参数：[field, introspectedTable, introspectedColumn]
     *返 回 值：void
     *描    述：给字段加入备注
     **/
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("@ApiModelProperty(value = \""+introspectedColumn.getRemarks()+"\")");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    /**
     *开 发 者：连磊
     *开发时间：2019/7/25 17:18
     *方 法 名：addModelClassComment
     *传入参数：[topLevelClass, introspectedTable]
     *返 回 值：void
     *描    述：在类上添加需要的东西
     **/
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* 作者："+System.getProperty("user.name"));
        topLevelClass.addJavaDocLine("* 时间："+ LocalDate.now() + " "+ LocalTime.now());
        topLevelClass.addJavaDocLine("* 描述："+introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine("*/");

        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");

        topLevelClass.addJavaDocLine("@Data");
        topLevelClass.addJavaDocLine("@ApiModel(description = \""+introspectedTable.getRemarks()+"\")");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {


    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }
}
