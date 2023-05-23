package de.htwg.se.schwimmen.fieldComponent.fieldImpl

import scala.concurrent.duration.*
import scala.concurrent.duration.*
import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}
import org.mongodb.scala.*
import org.mongodb.scala.model.Aggregates.*
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.model.Projections.*
import org.mongodb.scala.model.Sorts.*
import org.mongodb.scala.model.Updates.*
import org.mongodb.scala.model.*
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.connection.ClusterSettings

object PlayerDAOmongoDB {

  def insertPlayer(playerId: Long, name: String, life: Int, hasKnocked: String,
                   fCardValue: String, fCardColor: String,
                   sCardValue: String, sCardColor: String,
                   tCardValue: String, tCardColor: String, turn: String) = {

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Players")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Players")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val document: Document = Document("name" -> name, "life" -> life, "hasKnocked" -> hasKnocked,
      "fCardValue" -> fCardValue, "fCardColor" -> fCardColor, "sCardValue" -> sCardValue, "sCardColor" -> sCardColor,
      "tCardValue" -> tCardValue, "tCardColor" -> tCardColor, "turn" -> turn)
      val insertObservable = collection.insertOne(document)
      Await.result(insertObservable.toFuture(), 10.seconds)
      println("INSERT-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
  }

  def getAllPlayers(): Seq[(Long, String, Int, String,
    String, String,
    String, String,
    String, String, String)] = {

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Players")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    var retList: Seq[(Long, String, Int, String, String, String, String, String, String, String, String)] = List()
    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Players")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val findObservable = collection.find()
      val futureResult: Future[Seq[Document]] = findObservable.toFuture()
      val documents: Seq[Document] = Await.result(futureResult, 10.seconds)

      documents.foreach(doc => {
        val name = doc.getString("name")
        val life = doc.getInteger("life")
        val hasKnocked = doc.getString("hasKnocked")
        val fCardValue = doc.getString("fCardValue")
        val fCardColor = doc.getString("fCardColor")
        val sCardValue = doc.getString("sCardValue")
        val sCardColor = doc.getString("sCardColor")
        val tCardValue = doc.getString("tCardValue")
        val tCardColor = doc.getString("tCardColor")
        val turn = doc.getString("turn")
        retList = retList :+ (0L, name, life, hasKnocked, fCardValue, fCardColor,
          sCardValue, sCardColor, tCardValue, tCardColor, turn)
      })

      println("FIND-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
    retList
  }

  def deletePlayer(): Unit = {
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Players")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    var retList: Seq[(Long, String, Int, String, String, String, String, String, String, String, String)] = List()
    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Players")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val deleteObservable = collection.deleteMany(Document())
      Await.result(deleteObservable.toFuture(), 10.seconds)
      println("DELETE-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
  }

  def insertFieldCards(playingFieldId: Long,
                       fCardValue: String, fCardColor: String,
                       sCardValue: String, sCardColor: String,
                       tCardValue: String, tCardColor: String): Unit = {

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Field")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Field")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val document: Document = Document("fCardValue" -> fCardValue, "fCardColor" -> fCardColor, "sCardValue" -> sCardValue, "sCardColor" -> sCardColor,
        "tCardValue" -> tCardValue, "tCardColor" -> tCardColor)
      val insertObservable = collection.insertOne(document)
      Await.result(insertObservable.toFuture(), 10.seconds)
      println("INSERT-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
  }

  def getFieldCards(): Seq[(Long, String, String, String, String, String, String)] = {

    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Field")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    var retList: Seq[(Long, String, String, String, String, String, String)] = List()
    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Field")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val findObservable = collection.find()
      val futureResult: Future[Seq[Document]] = findObservable.toFuture()
      val documents: Seq[Document] = Await.result(futureResult, 10.seconds)

      documents.foreach(doc => {
        val fCardValue = doc.getString("fCardValue")
        val fCardColor = doc.getString("fCardColor")
        val sCardValue = doc.getString("sCardValue")
        val sCardColor = doc.getString("sCardColor")
        val tCardValue = doc.getString("tCardValue")
        val tCardColor = doc.getString("tCardColor")
        retList = retList :+ (0L, fCardValue, fCardColor,
          sCardValue, sCardColor, tCardValue, tCardColor)
      })

      println("FIND-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
    retList
  }

  def deletePlayingField(): Unit = {
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("SchwimmenDB")
    val collection: MongoCollection[Document] = database.getCollection("Field")

    // Überprüfung der Datenbank- und Sammlungsnamen
    val databaseNames: Seq[String] = Await.result(mongoClient.listDatabaseNames().toFuture(), 10.seconds)
    val collectionNames: Seq[String] = Await.result(database.listCollectionNames().toFuture(), 10.seconds)

    var retList: Seq[(Long, String, Int, String, String, String, String, String, String, String, String)] = List()
    if (!databaseNames.contains("SchwimmenDB")) {
      println("Die Datenbank 'SchwimmenDB' existiert nicht.")
    } else if (!collectionNames.contains("Field")) {
      println("Die Sammlung 'SchwimmenDB' existiert nicht.")
    } else {
      val deleteObservable = collection.deleteMany(Document())
      Await.result(deleteObservable.toFuture(), 10.seconds)
      println("DELETE-Operation erfolgreich durchgeführt.")
    }
    mongoClient.close()
  }

  def main(args: Array[String]): Unit = {
//    insertPlayer(0L, "Ayaz", 3, "false",
//      "king", "heart",
//      "king", "spade",
//      "king", "diamond", "false")
//    insertFieldCards(0L,"king", "heart",
//      "king", "spade",
//      "king", "diamond")
//    deletePlayer()
//    deletePlayingField()
//    println(getAllPlayers())
  }
}
