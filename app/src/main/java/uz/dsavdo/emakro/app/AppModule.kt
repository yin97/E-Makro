package uz.dsavdo.emakro.app

import org.koin.dsl.module
import uz.dsavdo.emakro.network.MakroRepository

val appModule= module {
    single { MakroRepository() }
}