package com.hugo.design.utilities

import com.hugo.design.R

enum class Circuit(val circuitId: String, val imageRes: Int) {
    ALBERT_PARK("albert_park", R.drawable.circuit_albert_park),
    SHANGHAI("shanghai", R.drawable.circuit_shanghai),
    SUZUKA("suzuka", R.drawable.circuit_suzuka),
    BAHRAIN("bahrain", R.drawable.circuit_bahrain),
    JEDDAH("jeddah", R.drawable.circuit_jeddah),
    MIAMI("miami", R.drawable.circuit_miami),
    IMOLA("imola", R.drawable.circuit_imola),
    MONACO("monaco", R.drawable.circuit_monaco),
    CATALUNYA("catalunya", R.drawable.circuit_catalunya),
    VILLENEUVE("villeneuve", R.drawable.circuit_villeneuve),
    REDBULLRING("red_bull_ring", R.drawable.circuit_red_bull_ring),
    SILVERSTONE("silverstone", R.drawable.circuit_silverstone),
    SPA("spa", R.drawable.circuit_spa),
    HUNGARORING("hungaroring", R.drawable.circuit_hungaroring),
    ZANDVOORT("zandvoort", R.drawable.circuit_zandvoort),
    MONZA("monza", R.drawable.circuit_monza),
    BAKU("baku", R.drawable.circuit_baku),
    MARINA_BAY("marina_bay", R.drawable.circuit_marina_bay),
    AMERICAS("americas", R.drawable.circuit_americas),
    RODRIGUEZ("rodriguez", R.drawable.circuit_rodriguez),
    BRAZIL("interlagos", R.drawable.circuit_rodriguez),
    VEGAS("vegas", R.drawable.circuit_vegas),
    LOSAIL("losail", R.drawable.circuit_losail),
    YAS_MARINA("yas_marina", R.drawable.circuit_yas_marina);


    companion object {
        fun getCircuitImageRes(circuitId: String): Int? {
            return entries.find { it.circuitId.equals(circuitId, ignoreCase = true) }?.imageRes
        }
    }
}

enum class Flag(val country: String, val imageRes: Int){
    AUSTRALIA("Australia", R.drawable.flag_australia),
    CHINA("China", R.drawable.flag_china),
    JAPAN("Japan", R.drawable.flag_japan),
    BAHRAIN("Bahrain", R.drawable.flag_bahrain),
    SAUDI_ARABIA("Saudi Arabia", R.drawable.flag_saudiarabia),
    USA("USA",R.drawable.flag_usa),
    ITALY("Italy",R.drawable.flag_italy),
    MONACO("Monaco",R.drawable.flag_monaco),
    SPAIN("Spain",R.drawable.flag_spain),
    CANADA("Canada",R.drawable.flag_canada),
    AUSTRIA("Austria",R.drawable.flag_austria),
    UK("UK", R.drawable.flag_uk),
    BELGIUM("Belgium", R.drawable.flag_belgium),
    HUNGARY("Hungary", R.drawable.flag_hungary),
    NETHERLANDS("Netherlands", R.drawable.flag_netherlands),
    AZERBAIJAN("Azerbaijan", R.drawable.flag_azerbaijan),
    SINGAPORE("Singapore", R.drawable.flag_singapore),
    MEXICO("Mexico", R.drawable.flag_mexico),
    BRAZIL("Brazil", R.drawable.flag_brazil),
    QATAR("Qatar", R.drawable.flag_qatar),
    UAE("UAE", R.drawable.flag_uae);

    companion object {
        fun getFlagImageRes(country: String): Int? {
            return entries.find { it.country.equals(country, ignoreCase = true) }?.imageRes
        }
    }

}

enum class Driver(val driverName: String, val imageRes: Int){
    HAMILTON(driverName = "hamilton", R.drawable.hamilton ),
    LECLERC(driverName = "leclerc", R.drawable.leclerc ),
    VERSTAPPEN(driverName = "verstappen", R.drawable.max ),
    PIASTRI(driverName = "piastri", R.drawable.oscar ),
    NORRIS(driverName = "norris", R.drawable.lando ),
    RUSSELL(driverName = "russell", R.drawable.george ),
    ANTONELLI(driverName = "antonelli", R.drawable.kimi ),
    TSUNODA(driverName = "tsunoda", R.drawable.tsunoda );

    companion object {
        fun getDriverImageRes(driverName: String): Int? {
            return Driver.entries.find { it.name.equals(driverName, ignoreCase = true) }?.imageRes
        }
    }
}

//enum class Constructor(val country: String, val imageRes: Int{
//
//}
