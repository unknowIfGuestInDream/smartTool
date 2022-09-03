package com.tlcsdm.diff;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author: 唐 亮
 * @date: 2022/9/3 16:54
 * @since: 1.0
 */
public class JavaDiffUtilsTest {

    /**
     * InsertDelta代表插入的，ChangeDelta代表删除的或修改的。position代表第几行，lines代表内容。
     */
    @Test
    public void javaDiffUtilsTest1() throws IOException {
        //原始文件
        List<String> original = Files.readAllLines(new File("E:\\testPlace\\diff\\test1.txt").toPath());
        //对比文件
        List<String> revised = Files.readAllLines(new File("E:\\testPlace\\diff\\test2.txt").toPath());

        //两文件的不同点
        Patch<String> patch = DiffUtils.diff(original, revised);
        for (AbstractDelta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }
    }

    /**
     * 减号代表原始文件test1.txt，加号代表对比文件test2.txt
     */
    @Test
    public void javaDiffUtilsTest2() throws IOException {
        //原始文件
        List<String> original = Files.readAllLines(new File("E:\\testPlace\\diff\\test1.txt").toPath());
        //对比文件
        List<String> revised = Files.readAllLines(new File("E:\\testPlace\\diff\\test2.txt").toPath());

        //两文件的不同点
        Patch<String> patch = DiffUtils.diff(original, revised);

        //生成统一的差异格式
        List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("test1.txt", "test2.txt", original, patch, 0);
        unifiedDiff.forEach(System.out::println);
    }

    /**
     * 根据unifiedDiff打补丁
     * <p>
     * 假设你有test1.txt、test2.txt、test3.txt 三个内容一样的文件，有一天你改了test2.txt的文件内容，
     * 你想把test2.txt修改的地方也同步的修改运用到 test3.txt上就可以通过打补丁的方式来实现。
     * 简单的来说就是test1.txt、test2.txt进行对比得到 unifiedDiff ，
     * 把 unifiedDiff运用到 test3.txt 。（相当于git的不同分支的代码同步）
     */
    @Test
    public void javaDiffUtilsTest3() throws IOException, PatchFailedException {
        //原始文件
        List<String> original = Files.readAllLines(new File("E:\\testPlace\\diff\\test1.txt").toPath());
        //对比文件
        List<String> revised = Files.readAllLines(new File("E:\\testPlace\\diff\\test2.txt").toPath());

        //两文件的不同点
        Patch<String> patch = DiffUtils.diff(original, revised);

        //生成统一的差异格式
        List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("test1.txt", "test2.txt", original, patch, 0);

        //从文件或此处从内存导入统一差异格式到补丁
        Patch<String> importedPatch = UnifiedDiffUtils.parseUnifiedDiff(unifiedDiff);

        List<String> test3 = Files.readAllLines(new File("E:\\testPlace\\diff\\test3.txt").toPath());

        //将差异运用到其他文件打补丁，即将不同点运用到其他文件（相当于git的冲突合并）
        List<String> patchedText = DiffUtils.patch(test3, importedPatch);
        for (String patchedTextPow : patchedText) {
            System.out.println(patchedTextPow);
        }
    }

    /**
     * 对比两文件的不同点并按行显示不同
     */
    @Test
    public void javaDiffUtilsTest4() throws IOException {
        //原始文件
        List<String> original = Files.readAllLines(new File("E:\\testPlace\\diff\\test1.txt").toPath());
        //对比文件
        List<String> revised = Files.readAllLines(new File("E:\\testPlace\\diff\\test2.txt").toPath());

        //行比较器，原文件删除的内容用"~"包裹，对比文件新增的内容用"**"包裹
        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .oldTag(f -> "~")
                .newTag(f -> "**")
                .build();
        //通过行比较器对比得到每一行的不同
        List<DiffRow> rows = generator.generateDiffRows(original, revised);

        //输出每一行的原始文件和对比文件，每一行的原始文件和对比文件通过 "|"分割
        for (DiffRow row : rows) {
            System.out.println(row.getOldLine() + "|" + row.getNewLine());
        }
    }

    @Test
    public void javaDiffUtilsTest5() throws IOException {
        //对比 两个文件，获得不同点
        List<String> diffString = DiffHandleUtils.diffString("E:\\testPlace\\diff\\test1.txt", "E:\\testPlace\\diff\\test2.txt");
        //生成一个diff.html文件，打开便可看到两个文件的对比
        DiffHandleUtils.generateDiffHtml(diffString, "E:\\testPlace\\diff\\diff.html");
    }
}
