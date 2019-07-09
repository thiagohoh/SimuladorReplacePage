import java.util.*

class TablePage(private val numPagina:Int, var  memory: Memory){
    private var pageFault = 0
    private var listPage = mutableListOf<Page>()
    private var what = 0  // qual algoritimo
    private var fifoBoys = LinkedList<Page>()

   fun startTable(){
        var kk = 0

        memory.startFrame()
        for (j in kk until numPagina){
            listPage.add(Page(j,Integer.toBinaryString(j),-1))
        }

   }

    fun printTable(){ listPage.forEach {it.printPage() }  }
    //find page precisa achar o end fisico certo e nao o end logico ou se nao estiver na

    private fun findPage(pageFisica:String):Int{ // acha a pagina : Dado o end logico da pagina devolve o end fisico da memoria
      //  println("fidePAge")
        listPage.forEach { if (it.pageLogica == pageFisica){ // talvez if pagefisica == pageFisica
            println("HIT")
            it.pageAge++
            return it.pageFisica
        } }// procura a pagina

        getFrameFisicoAfertFinderPagerFail(pageFisica)
        println("EPA")
        return -1
    }

    private  fun getFrameFisicoAfertFinderPagerFail(pageFisica:String){

        listPage.forEach { if (it.pageFisica == pageFisica.toInt()) println("HITAO") }

    }

    private fun getPage(pageLogic:String):Page{
        listPage.forEach { if(it.pageLogica == pageLogic)return it }

        return Page(-1,"-1",-1)
    }

    private fun findeFrame(frameFisico:Int){ // acha o frame

        memory.frameinho.forEach { if (it.framePosix == frameFisico) println(it.framePosix ) }

    }
    private fun findFramePageFault(FrameFisico:String):Frame{// acha o frame na memoria se deu page fault
        println("page no found")
        pageFault++
        memory.frameinho.forEach { if(FrameFisico == it.frameFisico){
            println(it.frameFisico + it.data)
            return it
        }
        }
        return Frame(-1,"-1","-1",-1)
    }

    fun find(bite:String){ // procura um ender;o

        var bit = findPage(bite)
        println("bitizao $bit")
        if(bit != -1){// procura na tabela de paginas
            println("finde frame > $bit")
            findeFrame(bit)
        }else{// se deu page miss procura na memoria
            println("NOT FOund $bite")
            when (what){
                1 ->  fifo( findFramePageFault(bite))
                2 -> lastRUsed(findFramePageFault(bite))
            }

        }
    }

    fun getPosix(endLogico:Int){
        var p = endLogico.toString().subSequence(0,2)
    }

     private fun insertTable(data: Int){// insert page
        listPage.forEach { if(it.pageFisica == -1){
            it.pageFisica = data
            it.pageAge++
            fifoBoys.add(it)
            return
        } }
    }

    fun insertFrame(data:String):Int{// insert no frame #memoria
        var posixFrame: Int
        memory.frameinho.forEach { if (it.r == 0){
            it.data = data
            posixFrame = it.framePosix
            it.r = 1
            insertTable(posixFrame)
            return 0
            }
        }
        return -1
    }

    fun insertProcess(process:MutableList<String>){
        process.forEach { insertFrame(it) }
    }

    fun pageAgeIncrease(){
        listPage.forEach { if(it.pageFisica != -1) it.pageAge++ }
    }


     fun removePage(pagePosix:Int){
        listPage[pagePosix].pageFisica = -1
        listPage[pagePosix].pageAge = 0
    }

    private fun fifo(frame: Frame){
        listPage.forEach { if (it == fifoBoys.first){
            println("opa")
            //removePage(it.pagePosix)
            listPage[it.pagePosix].pageAge = 0
            listPage[it.pagePosix].pageFisica = frame.framePosix
            listPage[it.pagePosix].pageLogica = frame.frameFisico
            //listPage[it.pagePosix].pageLogica = frame.framePosix.toString()
            fifoBoys.poll()
            fifoBoys.add(it)
            printTable()
            return
        } }
    }

    private fun lastRUsed(frame:Frame){
        var pq = PriorityQueue<Page>(20){a,b -> a.pageAge + b.pageAge}

            listPage.forEach { pq.add(it)  }

        listPage.forEach { if(it == pq.first()){
            listPage[it.pagePosix].pageAge = 0
            listPage[it.pagePosix].pageFisica = frame.framePosix
            listPage[it.pagePosix].pageLogica = frame.frameFisico
            pq.poll()
            return
        }
        }


    }

    fun printPageFault(){    println("Page fault $pageFault")      }

    fun findProcess(process: MutableList<String>,algth:Int){
        what = algth
        pageFault = 0
        process.forEach { find(it) }
    }


}