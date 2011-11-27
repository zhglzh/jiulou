package org.jiulou.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	/**
	 * <p>
	 * Title: 正则表达式的研究
	 * </p>
	 * <p>
	 * Description: 最近在工作中常常用到一些正则表达式的使用问题，到网上去找介绍大多是一鳞半爪。求人不如
	 * 求已。一狠心，自己看!这两天利用我们项目两期之间的一点空闲对J2SE所支持的正则表达式来
	 * 了个彻底研究!代价是……就是浪废了部门近十二张白纸。闲话少说，书归正传。 原理：
	 * 正则表达式的原理是有限状态自动机，自动机内部有有限个状态，有一个初始状态，有一个
	 * 结束状态。自动机根据输入和自身内部的当前状态来决定下一步于什么。呵呵，这是很久以前学 的东东了也记不清了，大家只作参照吧。
	 * Java中的正则表达式： 从J2SE1.4起Java增加了对正则表达式的支持就是java.util.regex包，这个包中主要有
	 * 3个类:Pattern,代表模式，就是正则表达式自身，Matcher，是一个有限状态自动机，其实大多
	 * 数的活还是让Pattern类于了，Matcher往往只是简单的调用Pattern，不知道这是什么模式。这
	 * 两个类写的都很经典，还有不少算法在内值得有功力的人仔细研究一下。另一个是一个异常类当所 用正则表达式不正确时抛出，是运行时异常。 几个难点：
	 * 1.line terminator line terminator 中文意终结符，是指一个或两个字符组成的字符序列。java中的 所有line
	 * terminator: A newline (line feed) character ('\n'), -----------换行符(0A) A
	 * carriage-return character followed immediately by a newline character
	 * ("\r\n"), -----------回车+换行(0D0A) A standalone carriage-return character
	 * ('\r'), -----------回车(0D) A next-line character ('\u0085'),
	 * ------------下一行符？(？表示我也不知道是什么，请大家明白的给我发mail A line-separator character
	 * ('\u2028'), or ------------行分隔符？ A paragraph-separator character
	 * ('\u2029). ------------段落分隔符？ If UNIX_LINES mode is activated, then the
	 * only line terminators recognized are newline characters.
	 * 如果使用unix模式则只有\n被认为是line terminator，也就是在使用pattern时如下： Pattern
	 * p=Pattern.compile("正则表达式",Pattern.UNIX_LINE); 或 Pattern
	 * p=Pattern.compile("(?d)正则表达式") "."匹配除line terminator以外的所有字符(未指定DOTALL时)
	 * 在指定DOTAll模式时"."匹配所有字符 2.Quantifiers,Greedy,Reluctant and Possessive.
	 * 这几个词不太好译，原文是Greedy Quantifiers,Reluctant Quantifiers and Possessive
	 * Quantifiers凭我这点英语我只好译作贪婪的量子，不情愿的量子和占有欲强的量子？这也太搞笑了， 好在我理解了他们的意思。这点等下我细说。
	 * 3. 对于[a-zA-Z],[a-d[h-i]],[^a-f],[b-f&&[a-z]],[b-f&&[^cd]]等形式的理解
	 * 对于上述，原文用range,union,negation,intersection,subtraction等来描述
	 * range表是范围，union是并集，negation是取反，intersection是交集，subtraction
	 * 是……是减法？？反正是减去一部分的意思 range a-z 从a到z的小写字母 negation [^a-f]除了a-f之外所有的，全集是所有字符
	 * union [a-d[h-i]] a-d并h-i subtraction [b-f&&[^cd]] 就是b-f中除了cd以外的都是
	 * intersection[b-f&&[a-z]] 就是b-f与a-z中的公共部分
	 * 我总结了一下，其实就是方括号表示一个集合，集合中的元素用列举法表示如[abcd]，但太多
	 * 了怎么为？总不能把从a到z的全列举吧？那就用a-z表示且省略了方括号，交集用&&表示，并集
	 * 省略，差集(对subtraction译成差集还差不多)用交集和取反来表示。所以，以上的可表示为：
	 * [[a-z][A-Z]],[[a-d][h-i]],[^a-f],[[b-f]&&[a-z]],[[b-f]&&[^cd]]
	 * 这样是不是和我们的习惯相符了. 4.各个标志的意义 在生成pattern时可以同时使用几个标志来指定进行匹配时的方案。 用法形如：Pattern
	 * p=Pattern.compile(".*a?",Pattern.UNIX_LINES); 当同时指定多个标志时可以使用"|"操作符连接如：
	 * Pattern p=Pattern.compile(".*a?,Pattern.UNIX_LINES|Pattern.DOTALL);
	 * 也可以在表达式中指定如： Pattern p=Pattern.compile("(?d).*a?"); Pattern
	 * p=Pattern.compile("(?d)(?s).*a?"); 以上两个定义和前面两个对应等价 所有的标志如下： Constant
	 * Equivalent Embedded Flag Expression Pattern.CANON_EQ None Enables
	 * canonical equivalence Pattern.CASE_INSENSITIVE (?i) Enables
	 * case-insensitive matching Pattern.COMMENTS (?x) Permits whitespace and
	 * comments in pattern. Pattern.MULTILINE (?m) Enables multiline mode.
	 * Pattern.DOATALL (?s) Enables dotall mode Pattern.UNICODE_CASE (?u)
	 * Enables Unicode-aware case folding. Pattern.UNIX_LINES (?d) Enables Unix
	 * lines mode
	 * 
	 * CANON_EQ 指定使用规范等价模式？这个我理解的也有限，是不是说只要指定了这个模式则
	 * ascii码的'a'就可以和unicode的'a'还有XXX码的'a'相等？请教各位。(mail to me)
	 * 
	 * CASE_INSENSITIVE 指定使用大小写不敏感的匹配模式，这个好理解，但要注意这个标志只是
	 * 对ascii码有效，要使unicode在比较时也忽略大小写要同时指定UNICODE_CASE,就是要指定
	 * CASE_INSENSITIVE|UNICODE_CASE或使用(?i)(?u)
	 * 
	 * COMMENTS 指定使用注释和忽略空白，也就是".*a"==". *a #this is comments"我想这个
	 * 在正则表达式很大，而且是在文件中输入时比较有用，平时我看也用不上。
	 * 
	 * MULTILINE In multiline mode the expressions ^ and $ match just after or
	 * just before, respectively, a line terminator or the end of the input
	 * sequence. By default these expressions only match at the beginning and
	 * the end of the entire input sequence
	 * 指定使用多行匹配模式，在默认模式下，^和$分别只匹配一个输入的开始和结束。 在这种模式下，^和$ 除了匹配整个输入的开始和结束外还匹配一个line
	 * terminator的后边和 前边(不是前边和后边，就是说^匹配line terminator的后边$匹配line terminator的前边。
	 * 
	 * DOATALL 如指定了这个模式则"."可匹配任何字符包括line terminator
	 * 
	 * UNIX_LINES 指定这个模式时只有\n被认为是line terminator而\r和\r\n都不是
	 * 
	 * 其他的我一时想不起来了，在具体介绍时再说吧。
	 * </p>
	 */

	@Test
	public void regexTest() {
		String str1 = "";
		Object str = "";
		// 注意：\r,\n,\b等转义字符在java字符串常量中要写成\\r,\\n,\\b等，否则编译都过不去
		// \s匹配\r,\n,\r和空格
		System.out
				.println("\\s匹配\\r,\\n,\\r和空格 " + " \t\n\r".matches("\\s{4}"));
		// \S和\s互逆
		System.out.println("\\S和\\s互逆 " + "/".matches("\\S"));
		// .不匹配\r和\n
		System.out.println(".不匹配\\r和\\n " + "\r".matches("."));
		System.out.println("\n".matches("."));

		// \w匹配字母，数字和下划线
		System.out.println("\\w匹配字母，数字和下划线 " + "a8_".matches("\\w\\w\\w"));
		// \W和\w互逆
		System.out.println("\\W和\\w互逆 " + "&_".matches("\\W\\w"));
		// \d匹配数字
		System.out.println("\\d匹配数字 " + "8".matches("\\d"));
		// \D与\d互逆
		System.out.println("\\D与\\d互逆" + "%".matches("\\D"));
		// 两者都匹配但意文不同
		System.out.println("======================");
		System.out.println("表示\\000a匹配\\000a " + "\n".matches("\n"));
		System.out.println("表示\\n匹配换行 " + "\n".matches("\\n"));
		System.out.println("======================");
		// 两者都匹配但意文不同
		System.out.println("\r".matches("\r"));
		System.out.println("\r".matches("\\r"));
		System.out.println("======================");
		// ^匹配开头
		System.out.println("^匹配开头" + "hell".matches("^hell"));
		System.out.println("abc\nhell".matches("^hell"));
		// $匹配结束
		System.out.println("$匹配结束" + "my car\nabc".matches(".*ar$"));
		System.out.println("my car".matches(".*ar$"));
		// \b匹配界
		System.out.println("\\b匹配界 " + "bomb".matches("\\bbom."));
		System.out.println("bomb".matches(".*mb\\b"));
		// \B与\b互逆
		System.out.println("\\B与\\b互逆" + "abc".matches("\\Babc"));

		// [a-z]匹配a到z的小写字母
		System.out.println("[a-z]匹配a到z的小写字母" + "s".matches("[a-z]"));
		System.out.println("S".matches("[A-Z]"));
		System.out.println("9".matches("[0-9]"));

		// 取反
		System.out.println("取反" + "s".matches("[^a-z]"));
		System.out.println("S".matches("[^A-Z]"));
		System.out.println("9".matches("[^0-9]"));

		// 括号的作用
		System.out.println("括号的作用" + "aB9".matches("[a-z][A-Z][0-9]"));
		System.out.println("aB9bC6".matches("([a-z][A-Z][0-9])+"));
		// 或运算
		System.out.println("或运算" + "two".matches("two|to|2"));
		System.out.println("to".matches("two|to|2"));
		System.out.println("2".matches("two|to|2"));

		// [a-zA-z]==[a-z]|[A-Z]
		System.out.println("[a-zA-z]==[a-z]|[A-Z]" + "a".matches("[a-zA-Z]"));
		System.out.println("A".matches("[a-zA-Z]"));
		System.out.println("a".matches("[a-z]|[A-Z]"));
		System.out.println("A".matches("[a-z]|[A-Z]"));

		// 体会一下以下四个
		System.out.println("体会一下以下四个\n==========================");
		System.out.println(")".matches("[a-zA-Z)]"));
		System.out.println(")".matches("[a-zA-Z)_-]"));
		System.out.println("_".matches("[a-zA-Z)_-]"));
		System.out.println("-".matches("[a-zA-Z)_-]"));
		System.out.println("==========================");
		System.out.println("b".matches("[abc]"));
		// [a-d[f-h]]==[a-df-h]
		System.out.println("[a-d[f-h]]==[a-df-h]" + "h".matches("[a-d[f-h]]"));
		System.out.println("a".matches("[a-z&&[def]]"));
		// 取交集
		System.out.println("取交集" + "a".matches("[a-z&&[def]]"));
		System.out.println("b".matches("[[a-z]&&[e]]"));
		// 取并
		System.out.println("取并" + "9".matches("[[a-c][0-9]]"));
		// [a-z&&[^bc]]==[ad-z]
		System.out
				.println("[a-z&&[^bc]]==[ad-z]" + "b".matches("[a-z&&[^bc]]"));
		System.out.println("d".matches("[a-z&&[^bc]]"));
		// [a-z&&[^m-p]]==[a-lq-z]
		System.out.println("[a-z&&[^m-p]]==[a-lq-z]"
				+ "d".matches("[a-z&&[^m-p]]"));
		System.out.println("a".matches("\\p{Lower}"));
		// /注意以下体会\b的用法(注意，在字符串常量中十目直接写\b表退格，所以要写\\b
		System.out.println("*********************************");
		System.out.println("aawordaa".matches(".*\\bword\\b.*"));
		System.out.println("a word a".matches(".*\\bword\\b.*"));
		System.out.println("aawordaa".matches(".*\\Bword\\B.*"));
		System.out.println("a word a".matches(".*\\Bword\\B.*"));
		System.out.println("a word a".matches(".*word.*"));
		System.out.println("aawordaa".matches(".*word.*"));
		// 体会一下组的用法
		// 组的顺序，只数"("第一个为第一组第二个是第二组……
		// 第0组表示整个表达式
		System.out.println("**************test group**************");
		Pattern p = Pattern.compile("(([abc]+)([123]+))([-_%]+)");
		Matcher m = p.matcher("aac212-%%");
		System.out.println(m.matches());
		m = p.matcher("cccc2223%_%_-");
		System.out.println(m.matches());
		System.out.println("======test group======");
		System.out.println(m.group());
		System.out.println(m.group(0));
		System.out.println(m.group(1));
		System.out.println(m.group(2));
		System.out.println(m.group(3));
		System.out.println(m.group(4));
		System.out.println(m.groupCount());
		System.out.println("========test end()=========");
		System.out.println(m.end());
		System.out.println(m.end(2));
		System.out.println("==========test start()==========");
		System.out.println(m.start());
		System.out.println(m.start(2));
		// test backslash测试反向引用？
		Pattern pp1 = Pattern.compile("(\\d)\\1");// 这个表达式表示必须有两相同的数字出现
		// \1表示引用第一个组\n表示引用第n个组(必须用\\1而不能用\1因\1在字符串中另有意义(我也知道是什么)
		Matcher mm1 = pp1.matcher("3345");// 33匹配但45不匹配
		System.out.println("test backslash测试反向引用");
		System.out.println(mm1.find());
		System.out.println(mm1.find());

		// 体会以下不同
		System.out.println("==============test find()=========");
		System.out.println(m.find());
		System.out.println(m.find(2));

		System.out.println("这是从第三个字符(index=2)开始找的group结果");
		System.out.println(m.group());
		System.out.println(m.group(0));
		System.out.println(m.group(1));
		System.out.println(m.group(2));
		System.out.println(m.group(3));
		m.reset();
		System.out.println(m.find());
		// 测试一个模式可多次匹配一个串
		System.out.println("测试一个模式可多次匹配一个串");
		Pattern p1 = Pattern.compile("a{2}");
		Matcher m1 = p1.matcher("aaaaaa");
		// 这说明Matcher的matchs()方法是对事个字符串的匹配，
		System.out.println(m1.matches());
		System.out.println(m1.find());
		System.out.println(m1.find());
		System.out.println(m1.find());
		System.out.println(m1.find());
		// 再测试matchs()
		System.out.println("再测试matchs()");
		Pattern p2 = Pattern.compile("(a{2})*");
		Matcher m2 = p2.matcher("aaaa");
		System.out.println(m2.matches());
		System.out.println(m2.matches());
		System.out.println(m2.matches());
		// 所以find是在一个串中找有没有对应的模式，而matchs是完全匹配
		// test lookupat()
		System.out.println("test lookupat()");
		Pattern p3 = Pattern.compile("a{2}");
		Matcher m3 = p3.matcher("aaaa");
		System.out.println(p3.flags());
		System.out.println(m3.lookingAt());
		System.out.println(m3.lookingAt());
		System.out.println(m3.lookingAt());
		// 总结以上matchs()是整个匹配且总是从头开始，find是部分匹配且从上一次匹配结束时开始找
		// lookingAt也是从头开始，但是部分匹配
		System.out.println("======test 空白行========");
		System.out.println(" \n".matches("^[ \\t]*$\\n"));

		// 演示appendXXX的用法
		System.out.println("=================test append====================");
		Pattern p4 = Pattern.compile("cat");
		Matcher m4 = p4.matcher("one cat two cats in the yard");
		StringBuffer sb = new StringBuffer();
		boolean result = m4.find();
		int i = 0;
		System.out.println("one cat two cats in the yard");
		while (result) {
			m4.appendReplacement(sb, "dog");
			System.out.println(m4.group());
			System.out.println("第" + i++ + "次:" + sb.toString());
			result = m4.find();
		}
		System.out.println(sb.toString());
		m4.appendTail(sb);
		System.out.println(sb.toString());

		// test UNIX_LINES
		System.out.println("test UNIX_LINES");
		Pattern p5 = Pattern.compile(".", Pattern.UNIX_LINES);
		Matcher m5 = p5.matcher("\n\r");
		System.out.println(m5.find());
		System.out.println(m5.find());

		// test UNIX_LINES
		System.out.println("test UNIX_LINES");
		Pattern p6 = Pattern.compile("(?d).");
		Matcher m6 = p6.matcher("\n\r");
		System.out.println(m6.find());
		System.out.println(m6.find());

		// test UNIX_LINES
		System.out.println("test UNIX_LINES");
		Pattern p7 = Pattern.compile(".");
		Matcher m7 = p7.matcher("\n\r");
		System.out.println(m7.find());
		System.out.println(m7.find());

		// test CASE_INSENSITIVE
		System.out.println("test CASE_INSENSITIVE");
		Pattern p8 = Pattern.compile("a", Pattern.CASE_INSENSITIVE);
		Matcher m8 = p8.matcher("aA");
		System.out.println(m8.find());
		System.out.println(m8.find());
		System.out.println("test CASE_INSENSITIVE");
		Pattern p9 = Pattern.compile("(?i)a");
		Matcher m9 = p9.matcher("aA");
		System.out.println(m9.find());
		System.out.println(m9.find());
		System.out.println("test CASE_INSENSITIVE");
		Pattern p10 = Pattern.compile("a");
		Matcher m10 = p10.matcher("aA");
		System.out.println(m10.find());
		System.out.println(m10.find());

		// test COMMENTS
		System.out.println("test COMMENTS");
		Pattern p11 = Pattern.compile(" a a #ccc", Pattern.COMMENTS);
		Matcher m11 = p11.matcher("aa a a #ccc");
		System.out.println(m11.find());
		System.out.println(m11.find());
		System.out.println("test COMMENTS");
		Pattern p12 = Pattern.compile("(?x) a a #ccc");
		Matcher m12 = p12.matcher("aa a a #ccc");
		System.out.println(m12.find());
		System.out.println(m12.find());

		// test MULTILINE这个大家多试试参照我上面对多行模式的理解
		System.out.println("test MULTILINE");
		Pattern p13 = Pattern
				.compile("^.?", Pattern.MULTILINE | Pattern.DOTALL);
		Matcher m13 = p13.matcher("helloohelloo,loveroo");
		System.out.println(m13.find());
		System.out.println("start:" + m13.start() + "end:" + m13.end());
		System.out.println(m13.find());
		// System.out.println("start:"+m13.start()+"end:"+m13.end());
		System.out.println("test MULTILINE");
		Pattern p14 = Pattern.compile("(?m)^hell.*oo$", Pattern.DOTALL);
		Matcher m14 = p14.matcher("hello,Worldoo\nhello,loveroo");
		System.out.println(m14.find());
		System.out.println("start:" + m14.start() + "end:" + m14.end());
		System.out.println(m14.find());
		// System.out.println("start:"+m14.start()+"end:"+m14.end());
		System.out.println("test MULTILINE");
		Pattern p15 = Pattern.compile("^hell(.|[^.])*oo$");
		Matcher m15 = p15.matcher("hello,Worldoo\nhello,loveroo");
		System.out.println(m15.find());
		System.out.println("start:" + m15.start() + "end:" + m15.end());
		System.out.println(m15.find());
		// System.out.println("start:"+m15.start()+"end:"+m15.end());

		// test DOTALL
		System.out.println("test DOTALL");
		Pattern p16 = Pattern.compile(".", Pattern.DOTALL);
		Matcher m16 = p16.matcher("\n\r");
		System.out.println(m16.find());
		System.out.println(m16.find());

		System.out.println("test DOTALL");
		Pattern p17 = Pattern.compile(".");
		Matcher m17 = p17.matcher("\n\r");
		System.out.println(m17.find());
		System.out.println(m17.find());

		System.out.println("test DOTALL");
		Pattern p18 = Pattern.compile("(?s).");
		Matcher m18 = p18.matcher("\n\r");
		System.out.println(m18.find());
		System.out.println(m18.find());

		// test CANON_EQ这个是jdk的例子但我实在不明白是什么意思，向大家请教
		System.out.println("test CANON_EQ");
		Pattern p19 = Pattern.compile("a\u030A", Pattern.CANON_EQ);
		System.out.println(Character.getType('\u030A'));
		System.out.println("is" + Character.isISOControl('\u030A'));
		System.out.println("is" + Character.isUnicodeIdentifierPart('\u030A'));
		System.out.println(Character.getType('\u00E5'));
		System.out.println("is" + Character.isISOControl('\u00E5'));
		Matcher m19 = p19.matcher("\u00E5");
		System.out.println(m19.matches());
		System.out.println(Character.getType('\u0085'));
		System.out.println("is" + Character.isISOControl('\u0085'));

		// 注意下面三个例子体会Greedy,Reluctant and Possessive Quantifiers的不同
		Pattern ppp = Pattern.compile(".*foo");
		Matcher mmm = ppp.matcher("xfooxxxxxxfoo");
		/**
		 * Greedy quantifiers X? X, once or not at all X* X, zero or more times
		 * X+ X, one or more times X{n} X, exactly n times X(n,} X, at least n
		 * times X{n,m} X, at least n but not more than m times Greedy
		 * quantifiers是最常用的一种，如上，它的匹配方式是先匹配尽可能多的字符，当 这样造成整个表达式整体不能匹配时就退一个字符再试比如：
		 * .*foo与xfooxxxxxxfoo的匹配过程，.*先与整个输入匹配，发现这样不行，整个串不能匹配
		 * 于是退最后一个字符"o"再试，还不行，再退直到把foo都退出才发现匹配于是结束。因为这个过程
		 * 总是先从最大匹配开始到找到一个匹配，所以.*与之匹配的总是一个最大的，这个特点和资本家相似 故名贪婪的
		 */
		boolean isEnd = false;
		int k = 0;
		System.out.println("==========");
		System.out.println("xfooxxxxxxfoo");
		while (isEnd == false)
			try {
				System.out.println("the:" + k++);
				System.out.println(mmm.find());
				System.out.println(mmm.end());
			} catch (Exception e) {
				isEnd = true;
			}
		isEnd = false;
		Pattern ppp1 = Pattern.compile(".*?foo");
		Matcher mmm1 = ppp1.matcher("xfooxxxxxxfoo");
		/**
		 * Reluctant quantifiers X?? X, once or not at all X*? X, zero or more
		 * times X+? X, one or more times X{n}? X, exactly n times X(n,}? X, at
		 * least n times X{n,m}? X, at least n but not more than m times
		 * Reluctant quantifiers的匹配方式正好相反，它总是先从最小匹配开始，如果这时导致
		 * 整个串匹配失败则再吃进一个字符再试，如：
		 * .*?foo与xfooxxxxxxfoo的匹配过程，首先，.*与空串匹配，这时整个串匹配失败，于是
		 * 再吃一个x,这时发现整个串匹配成功，当再调用find时从上次匹配结束时开始找，先吃一个
		 * 空串，不行，再吃一个x，不行，……直到把中间所有x都吃掉才发现匹配成功。这种方式总
		 * 是从最小匹配开始所以它能找到最多次数的匹配，但第一匹配都是最小的。它的行为有点象雇佣 工人，总是尽可能少的于活，故名勉强的。
		 */
		k = 0;
		System.out.println("?????????????????????");
		System.out.println("xfooxxxxxxfoo");
		while (isEnd == false)
			try {
				System.out.println("the:" + k++);
				System.out.println(mmm1.find());
				System.out.println(mmm1.end());
			} catch (Exception e) {
				isEnd = true;
			}
		isEnd = false;
		Pattern pp2 = Pattern.compile(".*+foo");
		Matcher mm2 = pp2.matcher("xfooxxxxxxfoo");
		/**
		 * 
		 Possessive quantifiers X?+ X, once or not at all X*+ X, zero or more
		 * times X++ X, one or more times X{n}+ X, exactly n times X(n,}+ X, at
		 * least n times X{n,m}+ X, at least n but not more than m times
		 * Possessive quantifiers 这种匹配方式与Greedy方式相似，所不同的是它不够聪明，当
		 * 它一口吃掉所有可以吃的字符时发现不匹配则认为整个串都不匹配，它不会试着吐出几个。它的行 为和大地主相似，贪婪但是愚蠢，所以名曰强占的。
		 */

		int ii = 0;
		System.out.println("+++++++++++++++++++++++++++");
		System.out.println("xfooxxxxxxfoo");
		while (isEnd == false)
			try {
				System.out.println("the:" + ii++);
				System.out.println(mm2.find());
				System.out.println(mm2.end());
			} catch (Exception e) {
				isEnd = true;
			}
	}
}