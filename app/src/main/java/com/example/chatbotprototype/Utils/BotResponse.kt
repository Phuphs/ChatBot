package com.example.chatbotprototype.Utils

import com.example.chatbotprototype.Utils.Constants.OPEN_GOOGLE
import com.example.chatbotprototype.Utils.Constants.OPEN_SEARCH
import com.example.chatbotprototype.Utils.Constants.OPEN_YOUTUBE

object BotResponse {

    fun basicResponses(_message:String):String{
        val random=(0..3).random()

        val message=_message.lowercase()
        return when {

            message.contains("i fine")||message.contains("i good")->{
               when(random) {
               0->"Very Good!"
                   1-> "Glad That You Are!"
                   2->"Good Job!"
                   3->"Same Pinch But Gentle...hehe"
                   else->"error"
               }

            }
            message.contains("what can you do")->{
                "I'm an A.I. \n Made with the purpose to help the humankind...*cheesy line*"
            }


            message.contains("what are you")||message.contains("who are you")->{
                "I'm A ChatBot A.K.A Phuphs...\nFeel Free To Ask Me Anything...*smile*"
            }
            message.contains("how old are you")->{
                "Are You Serious??\n I'm older than you...(lol)"
            }
            message.contains("haha")||message.contains("hehe")||message.contains("lol")->{
                "Glad that you like my sarcasm...*blush*"
            }
                    message.contains("tell me ")&&message.contains("joke")->{
                        val r=(0..10).random()
                        when(r){
                        0 -> "Do you want to hear a construction joke?\nSorry, I’m still working on it"
                        1 -> "You heard the rumor going around about butter?\n" +
                                "Never mind, I shouldn’t spread it."
                        2 -> "You: What cartoon mouse walks on two feet?\n" +
                                "\n" +
                                "Them: Mickey Mouse\n" +
                                "\n" +
                                "You: What duck walks on two feet?\n" +
                                "\n" +
                                "Them: Donald Duck\n" +
                                "\n" +
                                "You: No, all ducks do!"
                        3 -> " What did the Buddhist ask the hot dog vendor?\n" +
                                "\n" +
                                "“Make me one with everything.”"
                        4 -> "A horse walks into a bar.\n" +
                                "\n" +
                                "The bartender says, “Why the long face?”"
                        5 -> "Can a kangaroo jump higher than the Empire State Building?\n" +
                                "\n" +
                                "Of course! The Empire State Building can’t jump."
                        6 -> "Why don’t seagulls fly over the bay?\n" +
                                "\n" +
                                "Because then they’d be bagels."
                        7 -> "What do you call malware on a Kindle?\n" +
                                "\n" +
                                "A bookworm."
                        8 -> "What did the tie say to the hat?\n" +
                                "\n" +
                                "You go on ahead. I’ll hang around."
                        9 -> "What kind of shoes does a spy wear?\n" +
                                "\n" +
                                "Sneakers."
                        10 -> "Why did the giraffe get such bad grades?\n" +
                                "\n" +
                                "He always had his head stuck in the clouds."
                        else->"Error"
                        }
            }
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }
            message.contains("search")-> {
                OPEN_SEARCH
            }
            message.contains("play")-> {
                OPEN_YOUTUBE
            }
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }
            message.contains("hello")||message.contains("hi") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Hi"
                    3 -> "Hasta La Vista Baby"
                    else -> "error" }
            }
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    3 -> "Just Taking A Piss...hehe"
                    else -> "error"
                }
            }
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different.."
                    2 -> "Idk.."
                    else -> "Error"
                }
            }
        }
    }
}