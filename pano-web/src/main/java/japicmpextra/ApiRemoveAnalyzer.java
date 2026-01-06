package japicmpextra;

import japicmp.model.*;

import java.util.List;

public class ApiRemoveAnalyzer {

    public static void analyze(List<JApiClass> jApiClasses) {

        for (JApiClass cls : jApiClasses) {

            String className = cls.getFullyQualifiedName();

            /* =========================
             * 1. 类级别
             * ========================= */
            if (cls.getChangeStatus() == JApiChangeStatus.REMOVED) {
                System.out.println("[CLASS REMOVED] " + className);
                continue;
            }

            printCompatibilityChanges(
                    "[CLASS]",
                    className,
                    cls.getCompatibilityChanges()
            );

            /* =========================
             * 2. 字段
             * ========================= */
            for (JApiField field : cls.getFields()) {
                String fieldName = className + "#" + field.getName();

                if (field.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[FIELD REMOVED] " + fieldName);
                }

                printCompatibilityChanges(
                        "[FIELD]",
                        fieldName,
                        field.getCompatibilityChanges()
                );
            }

            /* =========================
             * 3. 方法
             * ========================= */
            for (JApiMethod method : cls.getMethods()) {
                String methodName =
                        className + "#" + buildMethodSignature(method);

                if (method.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[METHOD REMOVED] " + methodName);
                }

                printCompatibilityChanges(
                        "[METHOD]",
                        methodName,
                        method.getCompatibilityChanges()
                );
            }

            /* =========================
             * 4. 构造函数
             * ========================= */
            for (JApiConstructor constructor : cls.getConstructors()) {
                String ctorName =
                        className + buildConstructorSignature(constructor);

                if (constructor.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[CONSTRUCTOR REMOVED] " + ctorName);
                }

                printCompatibilityChanges(
                        "[CONSTRUCTOR]",
                        ctorName,
                        constructor.getCompatibilityChanges()
                );
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
            if (!first) {
                sb.append(", ");
            }
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
            if (!first) {
                sb.append(", ");
            }
            sb.append(param.getType());
            first = false;
        }

        sb.append(")");
        return sb.toString();
    }

    /* =========================
     * CompatibilityChange 输出
     * ========================= */
    private static void printCompatibilityChanges(
            String level,
            String location,
            List<JApiCompatibilityChange> changes) {

        for (JApiCompatibilityChange change : changes) {

            // 只输出真正破坏兼容性的
            if (change.isBinaryCompatible() && change.isSourceCompatible()) {
                continue;
            }

            System.out.println(
                    level + " BREAKING: " +
                    location +
                    " | type=" + change.getType() +
                    " | binaryCompatible=" + change.isBinaryCompatible() +
                    " | sourceCompatible=" + change.isSourceCompatible()
            );
        }
    }
}
