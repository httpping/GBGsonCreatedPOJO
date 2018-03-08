package com.tp;

import com.intellij.codeInsight.actions.ReformatCodeAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.PsiElementFactoryImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.tp.json.JsonBeanCreated;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateJsonPojo extends AnAction  implements OnClickLinstener {

    String jsonValue ;
    Editor editor;
    Project project;
    PsiFile currentEditorFile;

    AnActionEvent anActionEvent;
    String currentEditorFileName;
    String className;
    private PsiClass psiClass;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
        this.anActionEvent = anActionEvent;
        project = anActionEvent.getData(PlatformDataKeys.PROJECT);

        editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile;
        currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

        currentEditorFileName = currentEditorFile.getName();

        /**
         * 文件名的前缀
         */
        psiClass = getPsiClassFromContext(anActionEvent,currentEditorFile,editor);
        className = currentEditorFileName.split("\\.")[0];
        showHintDialog(currentEditorFile, className, project);

//        CodeStyleManager.getInstance(project).reformat(psiClass);
//        new ReformatCodeAction().actionPerformed(anActionEvent);
    }


    /**
     * 获取事件的 PsiClass
     * @param e
     * @return
     */
    private PsiClass getPsiClassFromContext(AnActionEvent e,PsiFile psiFile,Editor editor) {

        if (psiFile == null || editor == null) {
            return null;
        }


        PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);

        return  psiClass;
    }


    private PsiElement getPsiElement(AnActionEvent e) {
        PsiFile psiFile = (PsiFile)e.getData(LangDataKeys.PSI_FILE);
        Editor editor = (Editor)e.getData(PlatformDataKeys.EDITOR);
        if(psiFile != null && editor != null) {
            int offset = editor.getCaretModel().getOffset();
            return psiFile.findElementAt(offset);
        } else {
            e.getPresentation().setEnabled(false);
            return null;
        }
    }

    /**
     * 显示提示对话框
     *
     * @param file
     * @param prefix
     * @param project
     */
    private void showHintDialog(PsiFile file, String prefix, Project project) {
        Demo1 dialog = new Demo1(this);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.requestFocus();
    }


    @Override
    public void onClick(String value) {
        System.out.println(value);
        JsonBeanCreated result = null;
        try {
              result = JsonBeanCreated.createdJavaBean(new JSONObject(value),false,className);
        } catch (JSONException e) {
            e.printStackTrace();
            result = new JsonBeanCreated();
            result.javaBean = new StringBuffer("异常 ： " +e.getMessage());
        }
        doCreate(anActionEvent,result.javaBean.toString());
    }

    /**
     * 初始化处理
     * @param e
     */
    private void doCreate(AnActionEvent e,String value) {
        //根据响应的事件 获取到当前事件所在的项目、编辑器、文件、
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        assert editor != null;
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();
        //根据编辑器获取当前的model、获取选中的文本
        String modelSelectedText = selectionModel.getSelectedText();
        //校验选中的文本

        // 得到convertBean
        StringBuffer stringBuffer = new StringBuffer(value);
        //写入到编辑器内容
        writeEditorStr(project, editor, document, selectionModel, stringBuffer);





    }



    /**
     * 写入到编辑器内容
     * @param project
     * @param editor
     * @param document
     * @param selectionModel
     * @param stringBuffer
     */
    private void writeEditorStr(Project project, Editor editor, Document document, SelectionModel selectionModel, StringBuffer stringBuffer) {
        //获取偏移量
        final int offset = editor.getCaretModel().getOffset();
        int lineNumber = document.getLineNumber(offset) + 1;
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        //创建线程 输入到编译器中

        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiField field = factory.createFieldFromText("public int a = 0;", psiClass);
//        psiClass.add(field);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //写入
                document.insertString(lineStartOffset, stringBuffer.toString());
                PsiDocumentManager manager = PsiDocumentManager.getInstance(project);
                manager.commitDocument(document);


                createdImport();

                CodeStyleManager.getInstance(project).reformat(psiClass);
//                new ReformatCodeAction().actionPerformed(anActionEvent);

            }
        };
        //执行写入
        WriteCommandAction.runWriteCommandAction(project, runnable);
        //移除掉选择的model
//        selectionModel.removeSelection();

    }


    public void createdImport(){
      /*  GlobalSearchScope searchScope = GlobalSearchScope.allScope(project);
        PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(psiClass.getClass().getName(), searchScope);

        PsiImportStatement importStatement = PsiElementFactoryImpl.SERVICE.getInstance(project).createImportStatement(psiClass);
        ((PsiJavaFile) psiClass.getContainingFile()).getImportList().add(importStatement);

        importStatement.getContainingFile().geti

        if (psiClasses!=null){

        }*/

    }
}
