fun main() {

    var memFisica = 64000 // 64kb 16bits
    var tamProcess = 32000 // 15 bits
    var tamPagina = 8000 // 4 bits
    var numFrames = (memFisica/tamPagina)//3bits
    var numPagina = tamProcess/tamPagina //2bits
    var deslocamento = tamPagina// 13 bits

    var endLogicoSize = Integer.toBinaryString(tamProcess).length
    var endFisicoSize = Integer.toBinaryString(memFisica).length

    var endLogico = 100110010011000

    //  println(numFrames)
    //  println(Integer.toBinaryString(8).length)

    // println("----")
    var  processo = mutableListOf<String>()
    fun createProcess(init: Int, end:Int){

        for (k in init ..end){
            processo.add(Integer.toBinaryString(k))
        }
    }

    createProcess(0,4)

    processo.forEach { println(it) }

    var frae  = Memory(8)
    var table = TablePage(numPagina,frae)

    table.startTable()


//    table.insertFrame("olar")
//    table.insertFrame("olar2")
//    table.insertFrame("olar3")
//    table.insertFrame("olar4")

    table.insertProcess(processo)
    table.printTable()
    println("===========")

    println("00000000000000000000000000000")
    table.findProcess(processo)
    table.printPageFault()
    //  table.printTable()
    //   table.printTable()

//    table.find("0")
//    table.find("1")
//    table.find("10")
//    table.find("11")
//    table.find("100")


  //  table.printTable()
   // table.find("100")




//    frae.startFrame()
////    frae.printFrame()
////    frae.findFrame("111")
////    frae.insertFrame(7,"111","carai")
////    frae.printFrame()



}

