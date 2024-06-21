package my.dahr.monopolyone.utils

enum class RankConverter(val range: IntRange, val word: String, val number: Int) {
    ROOKIE(0..4, "Rookie", 0),
    RECRUIT(5..9, "Recruit", 1),
    SOLDIER(10..14, "Soldier", 2),
    LANCE_CORPORAL(15..19, "Lance Corporal", 3),
    CORPORAL(20..24, "Corporal", 4),
    MASTER_CORPORAL(25..29, "Master Corporal", 5),
    LANCE_SERGEANT(30..34, "Lance Sergeant", 6),
    SERGEANT(35..39, "Sergeant", 7),
    MASTER_SERGEANT(40..44, "Master Sergeant", 8),
    SERGEANT_MAJOR(45..49, "Sergeant Major", 9),
    LIEUTENANT(50..54, "Lieutenant", 10),
    LIEUTENANT_MAJOR(55..59, "Lieutenant Major", 11),
    CAPTAIN(60..64, "Captain", 12),
    MAJOR(65..69, "Major", 13),
    LIEUTENANT_COLONEL(70..74, "Lieutenant Colonel", 14),
    COLONEL(75..79, "Colonel", 15),
    BRIGADIER_GENERAL(80..84, "Brigadier General", 16),
    GENERAL_MAJOR(85..89, "General Major", 17),
    GENERAL(90..94, "General", 18),
    LIEUTENANT_GENERAL(95..99, "Lieutenant General", 19),
    MARSHAL(100..10000, "Marshal", 20);

    companion object {
        fun fromNumberToRank(number: Int): String? {
            return entries.find { number in it.range }?.word
        }

        fun fromNumberToRankImageId(number: Int): Int? {
            return entries.find { number in it.range }?.number
        }
    }
}


