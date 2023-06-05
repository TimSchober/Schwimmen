package test

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadTest extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:8081")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("PostmanRuntime/7.32.2")
  
  private val headers_0 = Map("Postman-Token" -> "7cdfe16d-e45d-4a6d-9257-641d8ee57673")
  
  private val headers_1 = Map("Postman-Token" -> "86133a15-ab91-4a4b-b072-1ec41bd61051")
  
  private val headers_2 = Map(
  		"Content-Type" -> "application/json",
  		"Postman-Token" -> "d60b4102-bef8-436d-bdbc-7f28c961243a"
  )
  
  private val headers_3 = Map("Postman-Token" -> "cc105e37-5a28-4039-ba59-3c0c0a26bd3e")
  
  private val headers_4 = Map(
  		"Content-Type" -> "application/json",
  		"Postman-Token" -> "d4a8e84c-27de-44c4-9fb4-2676285effba"
  )
  
  private val headers_5 = Map(
  		"Content-Type" -> "application/json",
  		"Postman-Token" -> "ad207b5c-6e40-42a3-aec0-9e184bb22805"
  )
  
  private val headers_6 = Map(
  		"Content-Type" -> "application/json",
  		"Postman-Token" -> "ea04d1cc-2d3c-4a52-81a3-0d273d379a5e"
  )
  
  private val headers_7 = Map("Postman-Token" -> "36c55b0e-7db7-4be8-b7ff-ea9377a49f69")
  
  private val headers_8 = Map("Postman-Token" -> "6853bb3b-a2e4-4866-bc89-9480df1dc04e")
  
  private val headers_9 = Map("Postman-Token" -> "67d5ad90-fcba-4cd3-81e6-9eb618ec2506")
  
  private val headers_10 = Map("Postman-Token" -> "6eb13b90-b3ce-4208-9b5d-decae06a5d6b")
  
  private val headers_11 = Map("Postman-Token" -> "9ed2ec64-2e0e-4a8c-86eb-52b509dce71e")
  
  private val headers_12 = Map("Postman-Token" -> "9e2feeea-a2f1-462c-b5c8-46540b85f931")
  
  private val headers_13 = Map(
  		"Content-Type" -> "application/json",
  		"Postman-Token" -> "ea078161-a186-4955-86e7-541bd213faa9"
  )
  
  private val headers_14 = Map("Postman-Token" -> "51e08dc6-6d67-4b91-9f77-7241da61a66e")
  
  private val headers_15 = Map("Postman-Token" -> "080b2695-12d5-4fdb-8e3a-da8436f47815")
  
  private val headers_16 = Map("Postman-Token" -> "e19dd130-014e-40d3-8d1b-d4d69bd844f2")
  
  private val uri1 = "localhost"

  private val scn = scenario("RecordedSimulation")
    .exec(
      http("get_three_Cards_from_Stack1")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .exec(
      http("get_three_Cards_from_Stack2")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .exec(
      http("get_three_Cards_from_Stack3")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .exec(
      http("get_three_Cards_from_Stack4")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .exec(
      http("get_three_Cards_from_Stack5")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .exec(
      http("get_three_Cards_from_Stack6")
        .get("http://" + uri1 + ":8080/cardStack/threeCards")
        .headers(headers_0)
    )
    .pause(1)

    .exec(
      http("reset_Players_and_Playingfield")
        .get("/playersAndPlayingfield/reset")
        .headers(headers_1)
    )
    .pause(1)
    .exec(
      http("set_Player_Amount")
        .post("/playersAndPlayingfield/players/playeramount")
        .headers(headers_2)
        .body(RawFileBody("test/recordedsimulation/0002_request.json"))
    )
    .pause(1)
    .exec(
      http("get_ Player_Amount")
        .get("/playersAndPlayingfield/players/playeramount")
        .headers(headers_3)
    )
    .pause(1)
    .exec(
      http("add_a_Player")
        .post("/playersAndPlayingfield/players/playeradd")
        .headers(headers_4)
        .body(RawFileBody("test/recordedsimulation/0004_request.json"))
    )
    .pause(1)
    .exec(
      http("add_another_Player")
        .post("/playersAndPlayingfield/players/playeradd")
        .headers(headers_5)
        .body(RawFileBody("test/recordedsimulation/0005_request.json"))
    )
    .pause(1)
    .exec(
      http("set_Cards_on_Field")
        .post("/playersAndPlayingfield/playingField/cardsOnPlayingField")
        .headers(headers_6)
        .body(RawFileBody("test/recordedsimulation/0006_request.json"))
    )
    .pause(1)
    .exec(
      http("swap_all_Cards1")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards2")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards3")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards4")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards5")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
        .exec(
      http("swap_all_Cards6")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards7")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards8")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards9")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )
    .exec(
      http("swap_all_Cards10")
        .get("/playersAndPlayingfield/swapAllCards")
        .headers(headers_11)
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
