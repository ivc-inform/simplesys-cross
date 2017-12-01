package com.simplesys.common

import com.simplesys.common.JVM.Strings._

object URI extends ((String, String, String, String, String) => String) {

  /*Структура URI
URI = [ схема ":" ] иер-часть(url + [path]) [ "?" запрос ] [ "#" фрагмент ]
В этой записи:
схема
схема обращения к ресурсу (часто указывает на сетевой протокол), например http, ftp, file, mailto, path

иер-часть
содержит данные, обычно организованные в иерархической форме, которые, совместно с данными в неиерархическом компоненте запрос, служат идентификации ресурса в пределах видимости URI-схемы.
Обычно иер-часть содержит путь к ресурсу (и, возможно, перед ним, адрес сервера, на котором тот располагается) или идентификатор ресурса (в случае URN).

запрос
необязательный компонент URI состоит из параметров вида name=value разделенных '&'

фрагмент
(тоже необязательный компонент) позволяет косвенно идентифицировать вторичный ресурс посредством ссылки на первичный и указанием дополнительной информации. Вторичный идентифицируемый ресурс может
быть некоторой частью или подмножеством первичного, некоторым его представлением или другим ресурсом, определённым или описанным таким ресурсом.*/

  /*
      Пример

      http://www.ics.uci.edu/pub/ietf/uri/#Related
      то 9 вышеуказанных групп шаблона дадут следующие результаты соответственно:
      http: (1)
      http  (2)
      //www.ics.uci.edu (3)
      www.ics.uci.edu (4)
      /pub/ietf/uri/ (5)
      нет результата (6)
      нет результата (7)
      #Related       (8)
      Related        (9)
      */

  def apply(schema: String, url: String, path: String, query: String, fragment: String): String = {
    val res = (schema match {
      case "" => ""
      case null => ""
      case x => x + """://"""
    }) + (url match {
      case null => ""
      case x => x
    }) + (path match {
      case null => ""
      case x => x
    }) + (query match {
      case "" => ""
      case null => ""
      case x => "?" + x
    }) + (fragment match {
      case "" => ""
      case null => ""
      case x => "#" + x
    })

    res
  }

  def unapply(uri: String): Option[( /*schema: */ String, /*url: */ String, /*path: */ String, /*query: */ String, /*fragment:*/ String)] = {
    val URIPattern = """^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?""".r
    /*Этот шаблон включает в себя 9 обозначенных выше цифрами групп

 ^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?
 12            3  4          5       6  7        8 9

 группа 2 — схема,
 группа 4 — источник,
 группа 5 — путь,
 группа 7 — запрос,
 группа 9 — фрагмент.*/

    uri match {
      case URIPattern(_, schema, _, url, path, _, query, _, fragment) => Some(schema, url, path, query, fragment)
      case _ => None
    }

  }
}

