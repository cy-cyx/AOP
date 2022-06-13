package com.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.android.utils.ILogger;

import org.gradle.api.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Time:2022/6/9 10:39
 * Author:
 * Description:
 */
public class ASMTransform extends Transform {

    protected static final ILogger LOGGER =
        new LoggerWrapper(Logging.getLogger(ASMTransform.class));

    @Override
    public String getName() {
        return "testAMSTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation)
        throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

        // 当前是否是增量编译
        boolean isIncremental = transformInvocation.isIncremental();
        System.out.println("是否是增量编译" + isIncremental);

        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        //引用型输入，无需输出。
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        // 如果不是增量需要删掉旧数据
        if (!isIncremental) {
            outputProvider.deleteAll();
        }

        for (TransformInput input : inputs) {
            for (JarInput jarInput : input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                    jarInput.getFile().getAbsolutePath(),
                    jarInput.getContentTypes(),
                    jarInput.getScopes(),
                    Format.JAR);
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                FileUtils.copyFile(jarInput.getFile(), dest);
            }
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {

                File outputFile = outputProvider.getContentLocation(directoryInput.getName(),
                    directoryInput.getContentTypes(), directoryInput.getScopes(),
                    Format.DIRECTORY);
                File directoryFile = directoryInput.getFile();

                // 是否是增量编译
                if (isIncremental) {
                    Set<Map.Entry<File, Status>> entries = directoryInput.getChangedFiles().entrySet();
                    for (Map.Entry entity : entries) {
                        // 增量编译，仅需要处理变化的文件
                        File file = (File) entity.getKey();
                        Status status = (Status) entity.getValue();

                        switch (status) {
                            case NOTCHANGED: {
                                // nodo
                                break;
                            }
                            case ADDED:
                            case CHANGED: {
                                File outFile = getOutFile(directoryFile, file, outputFile);
                                outFile.delete();

                                // 判断是否要操作字节码


                                FileUtils.copyFile(file, outFile);
                                break;
                            }
                            case REMOVED: {
                                File outFile = getOutFile(directoryFile, file, outputFile);
                                outFile.delete();
                            }
                        }

                    }
                } else {
                    getAllChildFile(directoryInput.getFile(), new ChildFileCallback() {
                        @Override
                        public void onChildFile(File file) {
                            // 不是增量编译，目录下所有文件均需要处理
                            File outFile = getOutFile(directoryFile, file, outputFile);

                            // 判断是否要操作字节码

                            try {
                                FileUtils.copyFile(file, outFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    // 获得对应的输出路径
    private File getOutFile(File targetFile, File targetRootPathFile, File outRootPathFile) {
        String targetFilePath = targetFile.getAbsolutePath();
        String targetRootPath = targetRootPathFile.getAbsolutePath();
        String outRootPath = outRootPathFile.getAbsolutePath();
        String outFilePath = targetRootPath.replace(targetFilePath, outRootPath);
        File outFilePathFile = new File(outFilePath);
        outFilePathFile.mkdirs();
        if (!outFilePathFile.exists()) {
            try {
                outFilePathFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outFilePathFile;
    }

    // 获得目录下所有的子文件
    private void getAllChildFile(File root, ChildFileCallback callback) {
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (File file : files) {
                getAllChildFile(file, callback);
            }
        } else {
            callback.onChildFile(root);
        }
    }

    interface ChildFileCallback {
        void onChildFile(File file);
    }
}
