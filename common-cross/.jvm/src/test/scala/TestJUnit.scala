import com.simplesys.log.Logging
import junit.framework.TestCase
import com.simplesys.common.JVM.Strings._
import com.simplesys.common._

class TestJUnit extends TestCase with Logging {
    def testHelloWorld {
        //logger trace ("Hello World")

        val str = """{"Андрей":"ASD","Коля":1,"SPASSWORD":false,"SSECONDNAME":"\"абсдефж\"","SFIRSTNAME":"AS"}"""
        logger trace (s"${str.unQuoted}")
        logger trace (s"${"pieces/16/piece_blue.png".unQuoted}1")
        logger trace (s"str: ${"\"qwqwqwqw\"".unQuoted}")
    }

    def testNewLine {
        logger.debug("len: " + "com.simplesys.components.TreeGridEditor".newLine.newLine.space.length.toString)
        logger.debug("len: " + "com.simplesys.components.TreeGridEditor".newLine.newLine.space.chmp.length.toString)
    }

    def testShortClass {
        logger.debug("com.simplesys.components.TreeGridEditor".shortClassName)
        val s = space.newLine
    }

    def testtrplQuotWrp {
        logger.debug("len: " + "com.simplesys.components.TreeGridEditor".newLine.newLine.length.toString)
        logger.debug("len: " + "com.simplesys.components.TreeGridEditor".newLine.newLine.chmp.length.toString)
    }

    def teststrplQuotWrp {
        logger.debug("com.simplesys.components.TreeGridEditor".strplQuotWrp)
    }

    def testtrplOpt {
        def isOpr(x: Any) = {
            x match {
                case x: Array[String] => true
                case _ => false
            }
        }
    }

    def testDecodePercentEncoded {
        val input = "StkDocIntMove_Edit_Attr%26bm%3BStkDocIntMove_Edit_Attr.bmml%26bm%3BStkDocIntMove_Edit_Attr.bmml%26bm%3BStkDocIntMove_Edit_Attr.bmml%2C%2CStkDocIntMove_Edit_RSO%26bm%3BStkDocIntMove_Edit_RSO.bmml%26bm%3BStkDocIntMove_Edit_RSO.bmml%26bm%3BStkDocIntMove_Edit_RSO.bmml%2CStkDocIntMove_Edit_PSO%26bm%3BStkDocIntMove_Edit_PSO.bmml%26bm%3BStkDocIntMove_Edit_PSO.bmml%26bm%3BStkDocIntMove_Edit_PSO.bmml"
        logger.debug( s"""from "${input}" to "${input.decodePercentEncoded}"""")
        val parseResult = input.decodePercentEncoded.split(',').map(_.split( """&bm;""").last).map(Option(_).filter(_.trim.nonEmpty)).toIndexedSeq
        parseResult.foreach(x => logger.debug(x.toString))
        val input2 = "%u0410%u0442%u0440%u0438%u0431%u0443%u0442%u044B%2C%20%u0421%u043F%u0435%u0446%5C%u0438%u0444%u0438%u043A%5C%5C%u0430%u0446%u0438%u044F%5C%5C%2C2%2C%2C%20fk%3AfaWrntOut%2C%20fk%3AfaWrnt"
        logger.debug( s"""from "${input2}" to "${input2.decodePercentEncoded}"""")
        val parseResult2 = """(?<!\\),""".r.split(input2.decodePercentEncoded).map(x => Option(x.trim).filter(_.nonEmpty))
        parseResult2.foreach(x => logger.debug(x.toString))

    }

    def testLocalDateTime {
        val d = "2014-04-18T20:00:00.000".toLocalDateTime()
        logger debug d.toString
    }

    def testasDouble {
        //val d = "-3000 000".asDouble
        val d = "-3 000 000,22".asDouble
        logger debug d.toString
    }

    def testView1 {
        val v = (0 until 1000).view
        v foreach println
    }

    def testSeparators {
        logger debug s"DecimalSeparator: $DecimalSeparator"
        logger debug s"DroupingSeparator: $GroupingSeparator"
    }

    def testInsertInString {
        val str = "Иванов Иван Иванович"
        logger debug str
        logger debug str.insert("<span>", 0).insert("</span>", 12)
    }

    def testPlus {
        logger debug plus
    }

    def testLtrim: Unit = {
        val str = "  \n  weiewee  \n    adqewrer   \nedewrewrewrew  "
        logger debug str.toHex
        logger debug str.ltrim.toHex
    }

    def testRtrim: Unit = {
        val str = "  \n  weiewee  \n    adqewrer   \nedewrewrewrew  \n"
        logger debug str.toHex
        logger debug str.rtrim.toHex
    }

    def testSubstring: Unit ={
        val sum = "66 5000.00"
        logger debug sum.Substring(0, sum.lastIndexOf('.'))
    }

    def testGUID: Unit = {
        logger debug getGUID
        logger debug getGUID
        logger debug getGUID
        logger debug getGUID
    }
}
