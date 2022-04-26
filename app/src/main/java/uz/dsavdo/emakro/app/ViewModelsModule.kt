package uz.dsavdo.emakro.app

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.dsavdo.emakro.ui.enter.EnterViewModel

val viewModelsModule = module {
    viewModel { EnterViewModel(get()) }
}