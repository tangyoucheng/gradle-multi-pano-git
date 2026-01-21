package japicmpextra;

import static japicmp.util.StringHelper.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import japicmp.config.Options;
import japicmp.exception.JApiCmpException;
import japicmp.model.JApiAnnotation;
import japicmp.model.JApiAnnotationElement;
import japicmp.model.JApiAnnotationElementValue;
import japicmp.model.JApiBehavior;
import japicmp.model.JApiChangeStatus;
import japicmp.model.JApiClass;
import japicmp.model.JApiClassFileFormatVersion;
import japicmp.model.JApiClassType;
import japicmp.model.JApiCompatibility;
import japicmp.model.JApiCompatibilityChange;
import japicmp.model.JApiConstructor;
import japicmp.model.JApiException;
import japicmp.model.JApiField;
import japicmp.model.JApiGenericTemplate;
import japicmp.model.JApiGenericType;
import japicmp.model.JApiHasChangeStatus;
import japicmp.model.JApiHasGenericTemplates;
import japicmp.model.JApiHasGenericTypes;
import japicmp.model.JApiHasModifiers;
import japicmp.model.JApiImplementedInterface;
import japicmp.model.JApiJavaObjectSerializationCompatibility;
import japicmp.model.JApiMethod;
import japicmp.model.JApiModifier;
import japicmp.model.JApiReturnType;
import japicmp.model.JApiSerialVersionUid;
import japicmp.model.JApiSuperclass;
import japicmp.model.JApiType;
import japicmp.output.OutputFilter;
import japicmp.output.OutputGenerator;
import japicmp.output.html.HtmlOutput;
import japicmp.output.html.HtmlOutputGeneratorOptions;
import japicmp.output.html.TemplateEngine;
import japicmp.util.Streams;

public class OutputExcelGenerator extends OutputGenerator<HtmlOutput> {

	private final HtmlOutputGeneratorOptions htmlOutputGeneratorOptions;
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private static TemplateEngine templateEngine = new TemplateEngine();

	public OutputExcelGenerator(List<JApiClass> jApiClasses, Options options, HtmlOutputGeneratorOptions htmlOutputGeneratorOptions) {
		super(options, jApiClasses);
		this.htmlOutputGeneratorOptions = htmlOutputGeneratorOptions;
		this.templateEngine = new TemplateEngine();
	}

	@Override
	public HtmlOutput generate() {
		OutputFilter outputFilter = new OutputFilter(options);
		outputFilter.filter(jApiClasses);
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>").append(getTitle()).append("</title>\n");
		sb.append("<style>\n").append(getStyle()).append("\n</style>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<span class=\"title\">").append(getTitle()).append("</span>\n");
		sb.append("<br/>\n");
		metaInformation(sb);
		warningMissingClasses(sb);
		toc(sb);
		explanations(sb);
		classes(sb);
		sb.append("</body>\n");
		sb.append("</html>\n");
		return new HtmlOutput(sb.toString());
	}

	private void classes(StringBuilder sb) {
		if (options.isReportOnlySummary()) {
			return;
		}
//		sb.append(jApiClasses.stream()
//			.map(jApiClass -> templateEngine.loadAndFillTemplate("/html/class-entry.html", mapOf(
//				"fullyQualifiedName", jApiClass.getFullyQualifiedName(),
//				"outputChangeStatus", outputChangeStatus(jApiClass),
//				"javaObjectSerializationCompatible", javaObjectSerializationCompatible(jApiClass),
//				"modifiers", modifiers(jApiClass),
//				"classType", classType(jApiClass),
//				"compatibilityChanges", compatibilityChanges(jApiClass, false),
//				"classFileFormatVersion", classFileFormatVersion(jApiClass),
//				"genericTemplates", genericTemplates(jApiClass, false),
//				"superclass", superclass(jApiClass),
//				"interfaces", interfaces(jApiClass),
//				"serialVersionUid", serialVersionUid(jApiClass),
//				"fields", fields(jApiClass),
//				"constructors", constructors(jApiClass),
//				"methods", methods(jApiClass),
//				"annotations", annotations(jApiClass.getAnnotations())
//			)))
//			.collect(Collectors.joining()));
	}

	private String methods(JApiClass jApiClass) {
		if (!jApiClass.getMethods().isEmpty()) {
			return templateEngine.loadAndFillTemplate("/html/methods.html", mapOf(
				"tbody", methodsTBody(jApiClass.getMethods())
			));
		}
		return "";
	}

	private String methodsTBody(List<JApiMethod> methods) {
		return methods.stream()
			.sorted(Comparator.comparing(JApiMethod::getName))
			.map(method -> "<tr>\n" +
				"<td>" + outputChangeStatus(method) + "</td>\n" +
				"<td>" + modifiers(method) + "</td>\n" +
				"<td>" + genericTemplates(method, true) + "</td>\n" +
				"<td>" + returnType(method) + "</td>\n" +
				"<td>" + method.getName() + "(" + parameters(method) + ")" + annotations(method.getAnnotations()) + "</td>\n" +
				"<td>" + exceptions(method) + "</td>\n" +
				"<td>" + compatibilityChanges(method, true) + "</td>\n" +
				"<td>" +
				templateEngine.loadAndFillTemplate("/html/line-numbers.html", mapOf(
					"oldLineNumber", method.getOldLineNumberAsString(),
					"newLineNumber", method.getNewLineNumberAsString())) + "</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	public static String methodTBody(JApiMethod method,JApiChangeStatus jApiChangeStatus) {
//		JApiChangeStatus type = method.getChangeStatus();
        
		switch (jApiChangeStatus) {
		case REMOVED:
	        String parametersContent = parameters(method);
	        parametersContent = parametersContent.replace("Old:【", "");
	        parametersContent = parametersContent.replace("】", "");
			return 
					modifiers(method) +
					returnType(method) +
//					" " + method.getName() + "(" + parametersContent + ")" + annotations(method.getAnnotations()) + 
					" " + method.getName() + "(" + parametersContent + ")"  + 
					exceptions(method) 
				;
		case MODIFIED:
			return 
					modifiers(method) +
					returnType(method) +
					" " + method.getName() + "(" + parameters(method) + ")" + annotations(method.getAnnotations()) + 
					exceptions(method) 
				;
		case NEW:
			return 
					modifiers(method) +
					returnType(method) +
					" " + method.getName() + "(" + parameters(method) + ")" + annotations(method.getAnnotations()) + 
					exceptions(method) 
				;
		case UNCHANGED:
			return 
					modifiers(method) +
					returnType(method) +
					" " + method.getName() + "(" + parameters(method) + ")" + annotations(method.getAnnotations()) + 
					exceptions(method) 
				;
		}
		return "";
	}

	private static String returnType(JApiMethod method) {
		return 
			returnTypeValue(method.getReturnType()) 
			;
	}

	private static String returnTypeValue(JApiReturnType returnType) {
		switch (returnType.getChangeStatus()) {
			case NEW:
			case UNCHANGED:
				return returnType.getNewReturnType() + genericParameterTypes(returnType);
			case REMOVED:
				return returnType.getOldReturnType() + genericParameterTypes(returnType);
			case MODIFIED:
				return returnType.getNewReturnType() + " (<- " + returnType.getOldReturnType() + genericParameterTypes(returnType) + ")";
		}
		return "";
	}

	private String constructors(JApiClass jApiClass) {
		if (!jApiClass.getConstructors().isEmpty()) {
			return templateEngine.loadAndFillTemplate("/html/constructors.html", mapOf(
				"tbody", constructors(jApiClass.getConstructors())
			));
		}
		return "";
	}

	private String constructors(List<JApiConstructor> constructors) {
		return constructors.stream()
			.map(constructor -> "<tr>\n" +
				"<td>" + outputChangeStatus(constructor) + "</td>\n" +
				"<td>" + modifiers(constructor) + "</td>\n" +
				"<td>" + genericTemplates(constructor, true) + "</td>\n" +
				"<td>" + constructor.getName() + "(" + parameters(constructor) + ")" + annotations(constructor.getAnnotations()) + "</td>\n" +
				"<td>" + exceptions(constructor) + "</td>\n" +
				"<td>" + compatibilityChanges(constructor, true) + "</td>\n" +
				"<td>" +
				templateEngine.loadAndFillTemplate("/html/line-numbers.html", mapOf(
					"oldLineNumber", constructor.getOldLineNumberAsString(),
					"newLineNumber", constructor.getNewLineNumberAsString())) + "</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	public static String constructor(JApiConstructor constructor,JApiChangeStatus jApiChangeStatus) {
		switch (jApiChangeStatus) {
		case REMOVED:
			return 
					 modifiers(constructor) + 
//					 constructor.getName() + "(" + parameters(constructor) + ")" + annotations(constructor.getAnnotations()) + 
					 constructor.getName() + "(" + parameters(constructor) + ")"  + 
					 exceptions(constructor) 
//					 compatibilityChanges(constructor, true) + "</td>\n" +
					
//					templateEngine.loadAndFillTemplate("/html/line-numbers.html", mapOf(
//						"oldLineNumber", constructor.getOldLineNumberAsString(),
//						"newLineNumber", constructor.getNewLineNumberAsString())) + "</td>\n" +
//					"</tr>\n")
				;
		case MODIFIED:
			return 
					 modifiers(constructor) + 
					 constructor.getName() + "(" + parameters(constructor) + ")" + annotations(constructor.getAnnotations()) + 
					 exceptions(constructor) 
//					 compatibilityChanges(constructor, true) + "</td>\n" +
					
//					templateEngine.loadAndFillTemplate("/html/line-numbers.html", mapOf(
//						"oldLineNumber", constructor.getOldLineNumberAsString(),
//						"newLineNumber", constructor.getNewLineNumberAsString())) + "</td>\n" +
//					"</tr>\n")
				;
		case UNCHANGED:
			return 
					 modifiers(constructor) + 
					 constructor.getName() + "(" + parameters(constructor) + ")" + annotations(constructor.getAnnotations()) + 
					 exceptions(constructor) 
//					 compatibilityChanges(constructor, true) + "</td>\n" +
					
//					templateEngine.loadAndFillTemplate("/html/line-numbers.html", mapOf(
//						"oldLineNumber", constructor.getOldLineNumberAsString(),
//						"newLineNumber", constructor.getNewLineNumberAsString())) + "</td>\n" +
//					"</tr>\n")
				;
		}
		return "";
	}

	private static String exceptions(JApiBehavior jApiBehavior) {
		if (!jApiBehavior.getExceptions().isEmpty()) {
			return " throws " + exceptionsTBody(jApiBehavior.getExceptions());
		}
		return "";
	}

	private static String exceptionsTBody(List<JApiException> exceptions) {
		return exceptions.stream()
			.map(exc -> 
				exc.getName()
				)
			.collect(Collectors.joining(", "));
	}

	private static String parameters(JApiBehavior jApiBehavior) {
		return jApiBehavior.getParameters().stream()
//			.map(parameter -> parameter.getChangeStatus().name().toLowerCase() + " " +
			.map(parameter -> 
				parameter.getType() +
				genericParameterTypes(parameter) +
				binaryAndSourceCompatibility(parameter) 
				)
			.collect(Collectors.joining("\n, "));
	}

	public static String fields(JApiClass jApiClass,JApiChangeStatus jApiChangeStatus) {
		if (!jApiClass.getFields().isEmpty()) {
//			return templateEngine.loadAndFillTemplate("/html/fields.html", mapOf(
//				"tbody", fields(jApiClass.getFields())
//			));
			return fields(jApiClass.getFields(),jApiChangeStatus);
		}
		return "";
	}

	private static String fields(List<JApiField> fields,JApiChangeStatus jApiChangeStatus) {
		return fields.stream()
			.sorted(Comparator.comparing(JApiField::getName))
			.map(field -> "<tr>\n" +
				"<td>" + outputChangeStatus(field) + "</td>\n" +
				"<td>" + modifiers(field) + "</td>\n" +
				"<td>" + type(field) + "</td>\n" +
				"<td>" + field.getName() + annotations(field.getAnnotations()) + "</td>\n" +
				"<td>" + compatibilityChanges(field, true) + "</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	public static String field(JApiField field,JApiChangeStatus jApiChangeStatus) {
//		JApiType type = field.getType();
		switch (jApiChangeStatus) {
		case REMOVED:
			return  modifiers(field) +
					type(field) +
//					field.getName() + annotations(field.getAnnotations())
					" " + field.getName() 
					;
		case MODIFIED:
			return  modifiers(field) +
					type(field) +
//					field.getName() + annotations(field.getAnnotations())
					" " + field.getName() 
					;
		case UNCHANGED:
			return  modifiers(field) +
					type(field) +
					" " + field.getName() + annotations(field.getAnnotations())
//					" " + field.getName() 
					;
		}
		return "";
	}

	private static String type(JApiField field) {
		return 
			typeValue(field) 
			;
	}

	private static String typeValue(JApiField field) {
		JApiType type = field.getType();
		switch (type.getChangeStatus()) {
			case NEW:
			case UNCHANGED:
				return type.getNewValue() + genericParameterTypes(field);
			case REMOVED:
				return type.getOldValue() + genericParameterTypes(field);
			case MODIFIED:
				return type.getNewValue() + " (<- " + type.getOldValue() + genericParameterTypes(field) + ")";
		}
		return "";
	}

	private static String annotations(List<JApiAnnotation> annotations) {
		if (!annotations.isEmpty()) {
//			return templateEngine.loadAndFillTemplate("/html/annotations.html", mapOf(
//				"tbody", annotationsTBody(annotations)
//			));
			return "\nAnnotations:\n" + annotationsTBody(annotations);
		}
		return "";
	}

	private static String annotationsTBody(List<JApiAnnotation> annotations) {
		return annotations.stream()
			.sorted(Comparator.comparing(JApiAnnotation::getFullyQualifiedName))
			.map(annotation -> 
//				"<tr>\n" +
//				"<td>" + outputChangeStatus(annotation) + "</td>\n" +
//				"<td>" + annotation.getFullyQualifiedName() + "</td>\n" +
				outputChangeStatus(annotation) +
				annotation.getFullyQualifiedName()+ "\n"
//				"<td>" + elements(annotation) + "</td>\n" +
//				"</tr>\n"
					)
			.collect(Collectors.joining());
	}

	private static String elements(JApiAnnotation annotation) {
		if (!annotation.getElements().isEmpty()) {
			return templateEngine.loadAndFillTemplate("/html/annotation-elements.html", mapOf(
				"tbody", annotationElements(annotation.getElements())
			));
		} else {
			return "n.a.";
		}
	}

	private static String annotationElements(List<JApiAnnotationElement> elements) {
		return elements.stream()
			.map(element -> "<tr>\n" +
				"<td>" + outputChangeStatus(element) + "</td>\n" +
				"<td>" + element.getName() + "</td>\n" +
				"<td>" + element.getOldElementValues().stream()
				.map(OutputExcelGenerator::valueToString)
				.collect(Collectors.joining(",<wbr/>")) +
				"</td>\n" +
				"<td>" + element.getNewElementValues().stream()
				.map(OutputExcelGenerator::valueToString)
				.collect(Collectors.joining(",<wbr/>")) +
				"</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	private static String valueToString(JApiAnnotationElementValue value) {
		switch (value.getType()) {
			case Annotation:
				return "@" + value.getFullyQualifiedName() + "(" + values(value) + ")";
			case Array:
				return "{" + values(value) + "}";
			case Enum:
				return value.getFullyQualifiedName() + "." + value.getValue();
			default:
				return value.getValueString();
		}
	}

	private static String values(JApiAnnotationElementValue value) {
		return value.getValues().stream()
			.map(OutputExcelGenerator::valueToString)
			.collect(Collectors.joining());
	}

	private String serialVersionUid(JApiClass jApiClass) {
		if (jApiClass.getSerialVersionUid().isSerializableOld() || jApiClass.getSerialVersionUid().isSerializableNew()) {
			return templateEngine.loadAndFillTemplate("/html/serial-version-uid.html", mapOf(
				"tbody", serialVersionUidTBody(jApiClass.getSerialVersionUid())
			));
		}
		return "";
	}

	private String serialVersionUidTBody(JApiSerialVersionUid serialVersionUid) {
		return "<tr>\n" +
			"<td class=\"matrix_layout\">Old</td>" +
			"<td class=\"" + (serialVersionUid.isSerializableOld() != serialVersionUid.isSerializableNew() ? "modified" : "") + "\">" + serialVersionUid.isSerializableOld() + "</td>\n" +
			"<td class=\"" + (!serialVersionUid.getSerialVersionUidDefaultOldAsString().equals(serialVersionUid.getSerialVersionUidDefaultNewAsString()) ? "modified" : "") + "\">" + serialVersionUid.getSerialVersionUidDefaultOldAsString() + "</td>\n" +
			"<td class=\"" + (!serialVersionUid.getSerialVersionUidInClassOldAsString().equals(serialVersionUid.getSerialVersionUidInClassNewAsString()) ? "modified" : "") + "\">" + serialVersionUid.getSerialVersionUidInClassOldAsString() + "</td>\n" +
			"</tr>\n" +
			"<tr>\n" +
			"<td class=\"matrix_layout\">New</td>" +
			"<td class=\"" + (serialVersionUid.isSerializableOld() != serialVersionUid.isSerializableNew() ? "modified" : "") + "\">" + serialVersionUid.isSerializableNew() + "</td>\n" +
			"<td class=\"" + (!serialVersionUid.getSerialVersionUidDefaultOldAsString().equals(serialVersionUid.getSerialVersionUidDefaultNewAsString()) ? "modified" : "") + "\">" + serialVersionUid.getSerialVersionUidDefaultNewAsString() + "</td>\n" +
			"<td class=\"" + (!serialVersionUid.getSerialVersionUidInClassOldAsString().equals(serialVersionUid.getSerialVersionUidInClassNewAsString()) ? "modified" : "") + "\">" + serialVersionUid.getSerialVersionUidInClassNewAsString() + "</td>\n" +
			"</tr>\n";
	}

	private String interfaces(JApiClass jApiClass) {
		if (!jApiClass.getInterfaces().isEmpty()) {
			return templateEngine.loadAndFillTemplate("/html/interfaces.html", mapOf(
				"tbody", interfacesTBody(jApiClass.getInterfaces())
			));
		}
		return "";
	}

	private String interfacesTBody(List<JApiImplementedInterface> interfaces) {
		return interfaces.stream()
			.map(interfaze -> "<tr>\n" +
				"<td>" + outputChangeStatus(interfaze) + "</td>\n" +
				"<td>" + interfaze.getFullyQualifiedName() + "</td>\n" +
				"<td>" + compatibilityChanges(interfaze, true) + "</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	private String superclass(JApiClass jApiClass) {
		JApiSuperclass superclass = jApiClass.getSuperclass();
		if ((superclass.getOldSuperclass().isPresent() || superclass.getNewSuperclass().isPresent()) &&
			((superclass.getChangeStatus() == JApiChangeStatus.NEW && !superclass.getSuperclassNew().equalsIgnoreCase("java.lang.Object")) ||
				(superclass.getChangeStatus() == JApiChangeStatus.REMOVED && !superclass.getSuperclassOld().equalsIgnoreCase("java.lang.Object")) ||
				(superclass.getChangeStatus() == JApiChangeStatus.MODIFIED) ||
				(superclass.getChangeStatus() == JApiChangeStatus.UNCHANGED && !superclass.getSuperclassOld().equalsIgnoreCase("java.lang.Object"))
			)
		) {
			return templateEngine.loadAndFillTemplate("/html/superclass.html", mapOf(
				"tbody", superclassTBody(jApiClass.getSuperclass())
			));
		}
		return "";
	}

	private String superclassTBody(JApiSuperclass superclass) {
		return "<tr>\n" +
			"<td>" + outputChangeStatus(superclass) + "</td>\n" +
			"<td>" + superclassName(superclass) + "</td>\n" +
			"<td>" + compatibilityChanges(superclass, true) + "</td>\n" +
			"</tr>\n";
	}

	private String superclassName(JApiSuperclass superclass) {
		switch (superclass.getChangeStatus()) {
			case NEW:
			case UNCHANGED:
				return superclass.getSuperclassNew();
			case REMOVED:
				return superclass.getSuperclassOld();
			case MODIFIED:
				return superclass.getSuperclassNew() + "(<- " + superclass.getSuperclassOld() + ")";
		}
		return "";
	}

	private String genericTemplates(JApiHasGenericTemplates jApiHasGenericTemplates, boolean withNA) {
		List<JApiGenericTemplate> genericTemplates = jApiHasGenericTemplates.getGenericTemplates();
		if (!genericTemplates.isEmpty()) {
			return "<span class=\"label_class_member\">Generic Templates:</span>\n" +
				templateEngine.loadAndFillTemplate("/html/generic-templates.html", mapOf(
					"tbody", genericTemplatesTBody(genericTemplates)
				));
		}
		return withNA ? "n.a." : "";
	}

	private String genericTemplatesTBody(List<JApiGenericTemplate> genericTemplates) {
		return genericTemplates.stream()
			.map(jApiGenericTemplate -> "<tr>\n" +
				"<td>" + outputChangeStatus(jApiGenericTemplate) + "</td>\n" +
				"<td>" + jApiGenericTemplate.getName() + "</td>\n" +
				"<td>" + jApiGenericTemplate.getOldType() + interfaceTypes(jApiGenericTemplate.getOldInterfaceTypes()) + "</td>\n" +
				"<td>" + jApiGenericTemplate.getNewType() + "</td>\n" +
				"<td>" + genericParameterTypes(jApiGenericTemplate) + "</td>\n" +
				"</tr>\n")
			.collect(Collectors.joining());
	}

	private String interfaceTypes(List<JApiGenericType> interfaceTypes) {
		if (!interfaceTypes.isEmpty()) {
			return interfaceTypes.stream()
				.map(interfaceType -> "&#038; " + interfaceType.getType() + genericParameterTypesRecursive(interfaceType))
				.collect(Collectors.joining());
		}
		return "";
	}

	private static String genericParameterTypesRecursive(JApiGenericType jApiGenericType) {
		if (!jApiGenericType.getGenericTypes().isEmpty()) {
			return "<" + jApiGenericType.getGenericTypes().stream()
				.map(jApiGenericType1 -> genericParameterWithWildcard(jApiGenericType1) + genericParameterTypesRecursive(jApiGenericType1))
				.collect(Collectors.joining(",")) +
				">";
		}
		return "";
	}

	private static String genericParameterWithWildcard(JApiGenericType jApiGenericType) {
		switch (jApiGenericType.getGenericWildCard()) {
			case NONE:
				return jApiGenericType.getType();
			case EXTENDS:
				return "? extends " + jApiGenericType.getType();
			case SUPER:
				return "? super " + jApiGenericType.getType();
			case UNBOUNDED:
				return "?";
		}
		return "";
	}

	private static String genericParameterTypes(JApiHasGenericTypes jApiHasGenericTypes) {
		if (!jApiHasGenericTypes.getNewGenericTypes().isEmpty() || !jApiHasGenericTypes.getOldGenericTypes().isEmpty()) {
			return "<" +
				genericTypes("New", jApiHasGenericTypes.getNewGenericTypes()) + " " +
				genericTypes("Old", jApiHasGenericTypes.getOldGenericTypes()) +
				">";
		}
		return "";
	}

	private static String genericTypes(String header, List<JApiGenericType> genericTypes) {
		if (!genericTypes.isEmpty()) {
			return header + ":【" +
				genericTypes.stream()
					.map(genericType -> 
						genericParameterWithWildcard(genericType) +
						genericParameterTypesRecursive(genericType) 
						)
					.collect(Collectors.joining(", ")) 
				+ "】";
		}
		return "";
	}

	private String classFileFormatVersion(JApiClass jApiClass) {
		if (jApiClass.getClassFileFormatVersion().getChangeStatus() == JApiChangeStatus.MODIFIED) {
			return templateEngine.loadAndFillTemplate("/html/class-file-format-version.html", mapOf(
				"tbody", classFileFormatVersionTBody(jApiClass)
			));
		}
		return "";
	}

	private String classFileFormatVersionTBody(JApiClass jApiClass) {
		JApiClassFileFormatVersion classFileFormatVersion = jApiClass.getClassFileFormatVersion();
		return "<tr>\n" +
			"<td>" + outputChangeStatus(classFileFormatVersion) + "</td>\n" +
			"<td>" + classFileFormatVersionString(classFileFormatVersion.getMajorVersionOld(), classFileFormatVersion.getMinorVersionOld()) + "</td>\n" +
			"<td>" + classFileFormatVersionString(classFileFormatVersion.getMajorVersionNew(), classFileFormatVersion.getMinorVersionNew()) + "</td>\n" +
			"</tr>\n";
	}

	private String classFileFormatVersionString(int majorVersion, int minorVersion) {
		if (majorVersion >= 0 && minorVersion >= 0) {
			return majorVersion + "." + minorVersion;
		}
		return "n.a.";
	}

	private static String compatibilityChanges(JApiCompatibility jApiClass, boolean withNA) {
		if (!jApiClass.getCompatibilityChanges().isEmpty()) {
			return templateEngine.loadAndFillTemplate("/html/compatibility-changes.html", mapOf(
				"tbody", jApiClass.getCompatibilityChanges().stream()
					.map(OutputExcelGenerator::compatibilityChange)
					.collect(Collectors.joining())
			));
		}
		return withNA ? "n.a." : "";
	}

	private static String compatibilityChange(JApiCompatibilityChange jApiCompatibilityChange) {
		return "<tr><td>" + jApiCompatibilityChange.getType() + "</td></tr>\n";
	}

	public static String classType(JApiClass jApiClass) {
		return 
			classTypeValue(jApiClass.getClassType()) 
			;
	}

	private static String classTypeValue(JApiClassType classType) {
		if (classType.getChangeStatus() == JApiChangeStatus.MODIFIED) {
			return classType.getNewType().toLowerCase() + " (<- " + classType.getOldType().toLowerCase() + ")";
		} else if (classType.getChangeStatus() == JApiChangeStatus.NEW || classType.getChangeStatus() == JApiChangeStatus.UNCHANGED) {
			return classType.getNewType().toLowerCase();
		} else if (classType.getChangeStatus() == JApiChangeStatus.REMOVED) {
			return classType.getOldType().toLowerCase();
		}
		return "";
	}

	public static String modifiers(JApiHasModifiers jApiHasModifiers) {
		return jApiHasModifiers.getModifiers().stream()
			.map(jApiModifier -> {
				String modifier = modifier(jApiModifier);
				if (!modifier.trim().isEmpty()) {
					return 
						modifier +
						" ";
				}
				return "";
			})
			.collect(Collectors.joining());
	}

	private static String modifier(JApiModifier<? extends Enum<? extends Enum<?>>> jApiModifier) {
		if (jApiModifier.getChangeStatus() == JApiChangeStatus.MODIFIED) {
			return jApiModifier.getValueNew() + " (<- " + jApiModifier.getValueOld() + ") ";
		} else if (jApiModifier.getChangeStatus() == JApiChangeStatus.UNCHANGED) {
			return jApiModifier.getValueNew().toLowerCase().startsWith("non") || jApiModifier.getValueNew().equalsIgnoreCase("package_protected") ? "" : jApiModifier.getValueNew().toLowerCase();
		} else if (jApiModifier.getChangeStatus() == JApiChangeStatus.NEW) {
			return jApiModifier.getValueNew().toLowerCase().startsWith("non") || jApiModifier.getValueNew().equalsIgnoreCase("package_protected") ? "" : jApiModifier.getValueNew().toLowerCase();
		} else if (jApiModifier.getChangeStatus() == JApiChangeStatus.REMOVED) {
			return jApiModifier.getValueOld().toLowerCase().startsWith("non") || jApiModifier.getValueOld().equalsIgnoreCase("package_protected") ? "" : jApiModifier.getValueOld().toLowerCase();
		}
		return "";
	}

	private String javaObjectSerializationCompatible(JApiClass jApiClass) {
		if (jApiClass.getJavaObjectSerializationCompatible() == JApiJavaObjectSerializationCompatibility.JApiJavaObjectSerializationChangeStatus.NOT_SERIALIZABLE) {
			return "";
		} else if (jApiClass.getJavaObjectSerializationCompatible() == JApiJavaObjectSerializationCompatibility.JApiJavaObjectSerializationChangeStatus.SERIALIZABLE_COMPATIBLE) {
			return "<span class=\"new\">&#160;(Serializable compatible)&#160;</span>";
		} else {
			return "<span class=\"removed\">&#160;(Serializable incompatible(!): " + jApiClass.getJavaObjectSerializationCompatibleAsString() + ")&#160;</span>";
		}
	}

	private void explanations(StringBuilder sb) {
		sb.append("<div class=\"explanations\">\n" +
			"<span>Binary incompatible changes are marked with (!) while source incompatible changes are marked with (*).</span>\n" +
			"</div>\n");
	}

	private void toc(StringBuilder sb) {
		if (!jApiClasses.isEmpty()) {
			sb.append("<ul>\n");
			sb.append("<li>\n");
			sb.append("<a href=\"#toc\">Classes</a>\n");
			sb.append("</li>\n");
			sb.append("</ul>\n");
			sb.append(templateEngine.loadAndFillTemplate("/html/toc.html", mapOf(
				"tbody", tocEntries()
			)));
		}
	}

	private String tocEntries() {
		return jApiClasses.stream()
			.map(jApiClass -> templateEngine.loadAndFillTemplate("/html/toc-entry.html", mapOf(
				"outputChangeStatus", outputChangeStatus(jApiClass),
				"fullyQualifiedName", jApiClass.getFullyQualifiedName()
			)))
			.collect(Collectors.joining());
	}

	private static String outputChangeStatus(JApiHasChangeStatus jApiHasChangeStatus) {
		return 
			jApiHasChangeStatus.getChangeStatus().name() +
			(jApiHasChangeStatus instanceof JApiCompatibility ? binaryAndSourceCompatibility((JApiCompatibility) jApiHasChangeStatus) : "") +
			"：";
	}

	private static String binaryAndSourceCompatibility(JApiCompatibility jApiCompatibility) {
		if (!jApiCompatibility.isBinaryCompatible()) {
			return " (!)";
		} else if (jApiCompatibility.isBinaryCompatible() && !jApiCompatibility.isSourceCompatible()) {
			return " (*)";
		}
		return "";
	}

	private void warningMissingClasses(StringBuilder sb) {
		if (options.getIgnoreMissingClasses().isIgnoreAllMissingClasses()) {
			sb.append("<div class=\"warnings\">\n" +
				"<span id=\"warning-missingclasses\">\n" +
				"WARNING: You are using the option '--ignore-missing-classes', i.e. superclasses and\n" +
				"interfaces that could not be found on the classpath are ignored. Hence changes\n" +
				"caused by these superclasses and interfaces are not reflected in the output.\n" +
				"</span>\n" +
				"</div>"
			);
		}
	}

	private String getStyle() {
		String styleSheet;
		if (options.getHtmlStylesheet().isPresent()) {
			try {
				InputStream inputStream = new FileInputStream(options.getHtmlStylesheet().get());
				styleSheet = Streams.asString(inputStream);
			} catch (FileNotFoundException e) {
				throw new JApiCmpException(JApiCmpException.Reason.IoException, "Failed to load stylesheet: " + e.getMessage(), e);
			}
		} else {
			styleSheet = templateEngine.loadTemplate("/style.css");
		}
		return styleSheet;
	}

	private void metaInformation(StringBuilder sb) {
		sb.append(templateEngine.loadAndFillTemplate("/html/meta-information.html", mapOf(
			"oldJar", options.joinOldArchives(),
			"newJar", options.joinNewArchives(),
			"newJar", options.joinNewArchives(),
			"creationTimestamp", DATE_FORMAT.format(new Date()),
			"accessModifier", options.getAccessModifier().name(),
			"onlyModifications", String.valueOf(options.isOutputOnlyModifications()),
			"onlyBinaryIncompatibleModifications", String.valueOf(options.isOutputOnlyBinaryIncompatibleModifications()),
			"ignoreMissingClasses", String.valueOf(options.getIgnoreMissingClasses().isIgnoreAllMissingClasses()),
			"packagesInclude", filtersAsString(options.getIncludes(), true),
			"packagesExclude", filtersAsString(options.getExcludes(), false),
			"semanticVersioning", htmlOutputGeneratorOptions.getSemanticVersioningInformation()
		))).append("\n");
	}

	private static Map<String, String> mapOf(String... args) {
		Map<String, String> map = new HashMap<>();
		int count = args.length / 2;
		for (int i = 0; i < count; i++) {
			map.put(args[i * 2], args[(i * 2) + 1]);
		}
		return map;
	}

	private String getTitle() {
		if (this.htmlOutputGeneratorOptions.getTitle().isPresent()) {
			return this.htmlOutputGeneratorOptions.getTitle().get();
		}
		return "japicmp-Report";
	}
}
