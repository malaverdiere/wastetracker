/**
 * JSON Stub Generator
 * (c) 2010, Daniel M. Lambea
 */
package org.json.tools.stubgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

/**
 * @author martind
 */
public class StubGen {
	
	private static final String STUB_TEMPLATE = "/stub.tpl";
	
	private static final String STUB_METHODTPL = "\tpublic %s decode%s(JSONObject jsonObj) {\n%s\t}\n";
	
	private static List<Class<?>> primitiveTypeList;
	
	static {
		primitiveTypeList = new Vector<Class<?>>();
		primitiveTypeList.add(Void.class);
		primitiveTypeList.add(String.class);
		primitiveTypeList.add(Byte.class);
		primitiveTypeList.add(Character.class);
		primitiveTypeList.add(Integer.class);
		primitiveTypeList.add(Long.class);
		primitiveTypeList.add(Double.class);
		primitiveTypeList.add(Float.class);
	}

	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				throw new StubGeneratorException("Error: debe especificarse una interfaz!");
			} else if (args.length > 2) {
				throw new StubGeneratorException("Error: demasiados parámetros: se debe especificar una interfaz y opcionalmente un paquete de salida!");
			} else {
				try {
					Class<?> ifaceClass = Class.forName(args[0]);
					if (!ifaceClass.isInterface()) {
						throw new StubGeneratorException("Error: debe especificarse una interfaz, no una clase! ;-)");
					}
					
					StubGen stubGen = new StubGen();
					String pkgName = (args.length == 2 ? args[1] : "stubgen");
					stubGen.generateStubsFor(ifaceClass, pkgName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new StubGeneratorException("Error: la interfaz especificada no se ha encontrado!");
				}
			}
		} catch(StubGeneratorException e) {
			System.err.println(e.getMessage());
		}
	}

	public void generateStubsFor(Class<?> ifaceClass, String pkgName) {
		List<Class<?>> processedTypes = new Vector<Class<?>>();
		StringWriter importsWriter = new StringWriter();
		StringWriter srcWriter = new StringWriter();
		
		for (Method m: ifaceClass.getMethods()) {
			String stubMethod;
			
			stubMethod = processType(m.getReturnType(), processedTypes);
			if (stubMethod != null) {
				srcWriter.write(stubMethod);
				srcWriter.write("\n");
			}
			
			for (Class<?> type: m.getParameterTypes()) {
				stubMethod = processType(type, processedTypes);
				if (stubMethod != null) {
					srcWriter.write(stubMethod);
					srcWriter.write("\n");
				}	
			}
		}

		for (Class<?> type: processedTypes) {
			importsWriter.write(String.format("import %s;\n", type.getName()));
		}
		importsWriter.write("\nimport org.json.JSONArray;\nimport org.json.JSONObject;");
		
		try {
			String template = readFileAsString(STUB_TEMPLATE);
			String finalSource = String.format(template, pkgName, importsWriter.toString(), ifaceClass.getSimpleName(),
					ifaceClass.getSimpleName(), srcWriter.toString());
			System.out.println(finalSource);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	protected String processType(Class<?> type, List<Class<?>> processedTypes) {
		while (type.isArray()) {
			type = type.getComponentType();
		}
		
		if (!type.isPrimitive() &&
				!primitiveTypeList.contains(type) &&
				!processedTypes.contains(type)) {
			processedTypes.add(type);
			
			int dummyVarCount = 0;
			StringWriter srcWriter = new StringWriter();
			
			srcWriter.write(String.format("\t\t%s result = new %s();\n", type.getSimpleName(), type.getSimpleName()));
			
			for (Method m: type.getMethods()) {
				/* Detectar un setter */
				if (m.getName().startsWith("set")) {
					Class<?>[] params = m.getParameterTypes();
					/* Los setters sólo tienen un parámetro */
					if (params.length == 1) {
						/* Detectar el nombre del atributo */
						String attribName = m.getName().substring(3);
						if (params[0].isPrimitive() || primitiveTypeList.contains(params[0])) {
							srcWriter.write(processPrimitiveType(attribName, params[0]));
						} else {
							srcWriter.write(processComplexType(dummyVarCount++, attribName, params[0]));
						}
					}
				}
			}
			
			srcWriter.write("\t\treturn result;\n");
			
			return String.format(STUB_METHODTPL, type.getSimpleName(), type.getSimpleName(), srcWriter.toString());
		}
		
		return null;
	}
	
	protected String processPrimitiveType(String attribName, Class<?> type) {
		StringBuffer txt = new StringBuffer();
		String attribLabel = getAttributeLabel(attribName);
		
		String typeName;
		if (type.isPrimitive()) {
			typeName = type.getSimpleName().substring(0, 1).toUpperCase() + type.getSimpleName().substring(1);
		} else {
			typeName = type.getSimpleName();
		}
			
		txt.append(String.format("\t\tresult.set%s(jsonObj.get%s(\"%s\"));\n", attribName, typeName, attribLabel));
		
		return txt.toString();
	}
	
	protected String processComplexType(int varSuffix, String attribName, Class<?> type) {
		StringBuffer txt = new StringBuffer();
		
		boolean isList = "List".equals(type.getSimpleName());
		
		/* Soporte para listas */
		if (type.isArray() || isList) {
			if (!isList) {
				type = type.getComponentType();
			}
			String attribLabel = getAttributeLabel(attribName);
			String typeName = type.getSimpleName();
			String typeNameInCaps;
			if (type.isPrimitive()) {
				typeNameInCaps = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
			} else {
				typeNameInCaps = typeName;
			}

			txt.append(String.format("\t\tJSONArray dummyJSONArray%d = jsonObj.getJSONArray(\"%s\");\n", varSuffix, attribLabel));
			txt.append(String.format("\t\tint len%d = dummyJSONArray%d.length();\n", varSuffix, varSuffix));
			txt.append(String.format("\t\t%s[] dummy%d = new %s[len%d];\n", typeName, varSuffix, typeName, varSuffix));
			txt.append(String.format("\t\tfor (int idx%d = 0; idx%d < len%d; idx%d++) {\n", varSuffix, varSuffix,
					varSuffix, varSuffix));
			if (type.isPrimitive() || isList) {
				txt.append(String.format("\t\t\tdummy%d[idx%d] = dummyJSONArray%d.get%s(idx%d);\n", varSuffix, varSuffix,
						varSuffix, typeNameInCaps, varSuffix));
			} else {
				txt.append(String.format("\t\t\tdummy%d[idx%d] = decode%s(dummyJSONArray%d.getJSONObject(idx%d));\n", 
						varSuffix, varSuffix, typeNameInCaps, varSuffix, varSuffix));
			}
			txt.append("\t\t}\n");
		} else {
			String typeName = type.getSimpleName();
			txt.append(String.format("\t\t%s dummy%d = decode%s(jsonObj.getJSONObject(\"%s\"));\n",
					typeName, varSuffix, typeName, attribName));
		}
		txt.append(String.format("\t\tresult.set%s(dummy%d);\n", attribName, varSuffix));
		
		return txt.toString();
	}
	
//	protected File initializeOutputFile(String prefix) {
//		File file = new File(String.format(STUB_FILENAME, prefix));
//	}
	
	protected String getAttributeLabel(String attribName) {
		if (attribName.length() == 1) {
			return attribName.toLowerCase();
		} else {
			return attribName.toLowerCase().substring(0, 1) + attribName.substring(1);
		}
	}
	
	protected String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
