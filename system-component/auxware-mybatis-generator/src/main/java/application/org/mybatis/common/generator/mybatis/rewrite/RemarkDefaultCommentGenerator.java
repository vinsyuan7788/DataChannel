package application.org.mybatis.common.generator.mybatis.rewrite;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class RemarkDefaultCommentGenerator extends DefaultCommentGenerator {

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {

		field.addJavaDocLine("/**"); //$NON-NLS-1$
		field.addJavaDocLine(" * <pre>");
		if (introspectedColumn.getRemarks() != null) {
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks()); //$NON-NLS-1$
		}

		field.addJavaDocLine(" * </pre>");
		field.addJavaDocLine(" * ");

		field.addJavaDocLine(" */"); //$NON-NLS-1$
	}

}
