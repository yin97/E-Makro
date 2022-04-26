package uz.dsavdo.emakro.network.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "Нет связи с сервером. Возможно отключена сеть!"
}