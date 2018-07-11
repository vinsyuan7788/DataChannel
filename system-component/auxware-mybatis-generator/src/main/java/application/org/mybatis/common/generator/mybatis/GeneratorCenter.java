package application.org.mybatis.common.generator.mybatis;

/**
 * 1、待配置参数说明：
 * 
 * <pre>
 * 1.1、本地修改不能提交的配置
 * GeneratorConstants.PROJECT_PATH：工程所在的目录
 * TEMP_PROJECT_PATH：临时目录
 * </pre>
 * 
 * <pre>
 * 1.2、模块相关配置：
 * modulePackage : 模块的包名。每添加一个模块，需要在代码中添加一个对应的块
 * tableMap: 表名，对应的实体名称。每增加一个表，需要添加一条记录
 * contextName: mybatis自动生成器中的context，命名规范：context_模块名
 * </pre>
 * 
 * 2、自动生成逻辑
 * 
 * <pre>
 * 2.1、CreateGeneratorXml根据配置参数，生成xml文件。
 * </pre>
 * 
 * <pre>
 * 2.2、greateGeneratorXml读取配置文件，解析配置文件。
 * </pre>
 * 
 * <pre>
 * 2.3、获取service, controller的信息。判断文件是否存在，如果存在则不重新生成。
 * </pre>
 * 
 * <pre>
 * 2.4、获取service, controller的信息。判断文件是否存在，如果存在则不重新生成。
 * </pre>
 * 
 * <pre>
 * 2.5、备份：对DAO进行备份
 * </pre>
 * 
 * <pre>
 * 2.6、配置文件去掉service,controller的配置部分，调用mybatis的自动生成方法，生成代码。
 * </pre>
 * 
 * <pre>
 * 2.7、model为覆盖生成。对自动生成的model，去掉其中的getter setter方法，并添加注解
 * </pre>
 * 
 * <pre>
 * 2.8、mapper.xml为覆盖生成，生成后向里面添加：(1)Query_Where_Clause条件;(1)getPageableList查询;(1)getListCount查询;
 * </pre>
 * 
 * <pre>
 * 2.9、mapper_expand.xml判断是否存在，存在则不生成。第一次是根据mapper.xml，只保留mapper标签，然后生成文件。
 * </pre>
 * 
 * <pre>
 * 2.10、判断备份的DAO是否存在，如果存在备份DAO，则进行还原。
 * </pre>
 * 
 * @author huanglijun
 *
 */
public class GeneratorCenter {

	private GeneratorCenter(){}
	
	public static void main(String[] args) throws Exception {
//		AbstractGeneratorRunner generatorRunner = new BasGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.BAS_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new BilGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new BusGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new CltGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new DevGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new FilGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new FltGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new LogGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new MsgGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new UsrGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		generatorRunner = new VsnGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();
//		
//		
//		generatorRunner = new WayGeneratorRunner();
//		generatorRunner.setProjectPath(GeneratorConstants.USR_PROJECT_PATH);
//		generatorRunner.generator();		
	}
}
