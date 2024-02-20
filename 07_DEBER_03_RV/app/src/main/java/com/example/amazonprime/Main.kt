package com.example.amazonprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amazonprime.adapter.ParentAdapter
import com.example.amazonprime.databinding.ActivityMainBinding
import com.example.amazonprime.utils.SampleData
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<CarouselItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar componentes
        initComponents()
        //initUI()
    }

    private fun initComponents() {
        binding.carousel.apply {
            list.add(CarouselItem("https://m.media-amazon.com/images/I/61LZzDxrf+L.jpg"))
            list.add(CarouselItem("https://www.centralweb.cl/wp-content/uploads/2022/12/hbo_max_lanza_posters_oficiales_de_los_personajes_de_la_anticipada_serie_the_last_of_us_1.jpg"))
            list.add(CarouselItem("https://i.pinimg.com/736x/5b/b8/b3/5bb8b3155711b590c18a61ca9df3ca0e.jpg"))
            list.add(CarouselItem("https://www.mubis.es/media/users/7286/261490/poster-hbo-max-de-la-segunda-temporada-de-doom-patrol-original.jpg"))
            list.add(CarouselItem("https://moviehole.net/img/Wonder-Woman-1984-Character-Poster.jpg"))
            list.add(CarouselItem("https://lh3.googleusercontent.com/proxy/yB3zzVor257n91wMLxNmCeEmcF66ZI7i-ad__WtzvbluuPslGVQOdmODSin2pMUqJef1bh74nIE5wskFyIcXrvSgwkw4aKT08EUa72rHiqCiF60cS_L7kp_MklBX4RglePm9eA"))
            list.add(CarouselItem("https://elcomercio.pe/resizer/94HTd3FmDayylM-URfvDIgCtxAY=/980x0/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/N2GKTUUFFJFMXGTYM4EJU5TIOE.jpg"))
            addData(list)
        }

        // Configurar el RecyclerView
        binding.rvParent.adapter = ParentAdapter(SampleData.collections)
    }
}

