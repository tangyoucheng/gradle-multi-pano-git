package japicmpextra;

import japicmp.model.*;

import java.util.List;

public class ModifiedBreakingAnalyzer {

    /**
     * 分析修改但破坏兼容性的 API
     * 只统计 JApiChangeStatus.MODIFIED 且破坏兼容性的修改
     * 排除所有 Serializable 相关变化：
     * 1. 类本身 Serializable incompatible
     * 2. 字段、方法、构造函数的 Serializable 相关变化
     */
    public static void analyzeBreakingModified(List<JApiClass> jApiClasses) {

        for (JApiClass cls : jApiClasses) {
        	boolean clsOutputFlag = true;
            String className = cls.getFullyQualifiedName();

            // =========================
            // 1. 类本身 Serializable incompatible
            // =========================
            String serCompat = cls.getJavaObjectSerializationCompatibleAsString().toLowerCase();
            if (serCompat.contains("incompatible")) {
                continue; // 整个类跳过
            }

            // =========================
            // 3. 字段 MODIFIED
            // =========================
            for (JApiField field : cls.getFields()) {
                if (field.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    for (JApiCompatibilityChange change : field.getCompatibilityChanges()) {
                        if (isBreakingAndNotSerializable(change)) {
                            System.out.println("[FIELD BREAKING MODIFIED] " + className + "#" + field.getName()
                                    + " | type=" + change.getType());
                            clsOutputFlag = false;
                        }
                    }
                }
            }

            // =========================
            // 4. 方法 MODIFIED
            // =========================
            for (JApiMethod method : cls.getMethods()) {
                if (method.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    for (JApiCompatibilityChange change : method.getCompatibilityChanges()) {
                        if (isBreakingAndNotSerializable(change)) {
                            System.out.println("[METHOD BREAKING MODIFIED] " + className + "#" + buildMethodSignature(method)
                                    + " | type=" + change.getType());
                            clsOutputFlag = false;
                        }
                    }
                }
            }

            // =========================
            // 5. 构造函数 MODIFIED
            // =========================
            for (JApiConstructor ctor : cls.getConstructors()) {
                if (ctor.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    for (JApiCompatibilityChange change : ctor.getCompatibilityChanges()) {
                        if (isBreakingAndNotSerializable(change)) {
                            System.out.println("[CONSTRUCTOR BREAKING MODIFIED] " + className + buildConstructorSignature(ctor)
                                    + " | type=" + change.getType());
                            clsOutputFlag = false;
                        }
                    }
                }
            }

            // =========================
            // 2. 类级别 MODIFIED
            // =========================
            if (clsOutputFlag && cls.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                for (JApiCompatibilityChange change : cls.getCompatibilityChanges()) {
                	//TODO 过滤序列化的地方还要再确认
                    if (!cls.getJavaObjectSerializationCompatible().isIncompatible() 
                    		&& isBreakingAndNotSerializable(change)) {
                        System.out.println("[CLASS BREAKING MODIFIED] " + className
                                + " | type=" + change.getType());
                    }
                }
            }
        }
    }

    /**
     * 判断是否破坏兼容性，且不是 Serializable 相关变化
     */
    private static boolean isBreakingAndNotSerializable(JApiCompatibilityChange change) {
        // 是否破坏兼容性
        boolean breaking = !change.isBinaryCompatible() || !change.isSourceCompatible();
        if (!breaking) {
            return false;
        }
        // 排除 Serializable 相关变化，包括 serialVersionUID
        String desc = change.toString().toLowerCase();
        return !(desc.contains("serializable") || desc.contains("serialversionuid") || desc.contains("serializable incompatible"));
    }

    /**
     * 构造方法签名
     */
    private static String buildMethodSignature(JApiMethod method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append("(");
        boolean first = true;
        for (JApiParameter param : method.getParameters()) {
            if (!first) sb.append(", ");
            sb.append(param.getType());
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 构造构造函数签名
     */
    private static String buildConstructorSignature(JApiConstructor ctor) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (JApiParameter param : ctor.getParameters()) {
            if (!first) sb.append(", ");
            sb.append(param.getType());
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }
}
