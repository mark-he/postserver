package com.eagletsoft.post.core.template;

import ognl.Ognl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplatePresenter {
    private Map<String, Object> variables;

    public TemplatePresenter(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String apply(String content) {
        if (null == content) {
            return "";
        }
        List<String> exps = findExpressions(content);
        return replaceValues(content, exps);
    }

    private String replaceValues(String content, List<String> exps) {
        for (String exp : exps) {
            String value = readValue(exp, variables);
            content = content.replace("${" + exp + "}", value);
        }
        return content;
    }

    private List<String> findExpressions(String content) {
        List<String> exps = new ArrayList<>();
        int idx = 0;

        while (true) {
            idx = content.indexOf("${", idx);
            if (idx >= 0) {
                int idx2 = content.indexOf("}", idx);
                if (idx2 >= 0) {
                    String exp =  content.substring(idx + 2, idx2);
                    exps.add(exp);
                    idx  = idx2;
                    continue;
                }
            }
            break;
        }
        return exps;
    }

    private String readValue(String expression, Object root)  {
        try {
            Object ret = Ognl.getValue(expression, variables);
            if (null == ret) {
                return "";
            }
            return ret.toString();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error in parse " + expression, ex);
        }
    }
}

