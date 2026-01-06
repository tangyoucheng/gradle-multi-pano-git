package japicmpextra;

import japicmp.model.*;

import java.util.List;

public class ApiChangeAnalyzer {

    /**
     * 分析被修改（MODIFIED）的 API，并输出破坏兼容性信息
     */
    public static void analyzeModified(List<JApiClass> jApiClasses) {

        for (JApiClass cls : jApiClasses) {
            String className = cls.getFullyQualifiedName();

            /* =========================
             * 1. 类级别修改
             * ========================= */
            if (cls.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                System.out.println("[CLASS MODIFIED] " + className);
                printCompatibilityChanges("[CLASS]", className, cls.getCompatibilityChanges());
            }

            /* =========================
             * 2. 字段修改
             * ========================= */
            for (JApiField field : cls.getFields()) {
                if (field.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String fieldName = className + "#" + field.getName();
                    System.out.println("[FIELD MODIFIED] " + fieldName);
                    printCompatibilityChanges("[FIELD]", fieldName, field.getCompatibilityChanges());
                }
            }

            /* =========================
             * 3. 方法修改
             * ========================= */
            for (JApiMethod method : cls.getMethods()) {
                if (method.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String methodName = className + "#" + buildMethodSignature(method);
                    System.out.println("[METHOD MODIFIED] " + methodName);
                    printCompatibilityChanges("[METHOD]", methodName, method.getCompatibilityChanges());
                }
            }

            /* =========================
             * 4. 构造函数修改
             * ========================= */
            for (JApiConstructor constructor : cls.getConstructors()) {
                if (constructor.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String ctorName = className + buildConstructorSignature(constructor);
                    System.out.println("[CONSTRUCTOR MODIFIED] " + ctorName);
                    printCompatibilityChanges("[CONSTRUCTOR]", ctorName, constructor.getCompatibilityChanges());
                }
            }
        }
    }

    /* =========================
     * 方法签名构造
     * ========================= */
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

    /* =========================
     * 构造函数签名构造
     * ========================= */
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

    /* =========================
     * 输出 CompatibilityChange
     * ========================= */
    private static void printCompatibilityChanges(
            String level,
            String location,
            List<JApiCompatibilityChange> changes) {

        for (JApiCompatibilityChange change : changes) {
            // 只输出真正破坏兼容性的修改
            if (change.isBinaryCompatible() && change.isSourceCompatible()) continue;

            System.out.println(level + " BREAKING: " +
                    location +
                    " | type=" + change.getType() +
                    " | binaryCompatible=" + change.isBinaryCompatible() +
                    " | sourceCompatible=" + change.isSourceCompatible()
            );
        }
    }
}
