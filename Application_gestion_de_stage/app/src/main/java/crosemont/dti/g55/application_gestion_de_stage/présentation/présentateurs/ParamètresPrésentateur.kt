package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import crosemont.dti.g55.application_gestion_de_stage.ecran_parametres

class ParamètresPrésentateur (private val vue: ecran_parametres) {

    companion object {
        private const val PRÉFS_NOM = "MesPréférences"
        private const val THÈME_MODE_KEY = "thèmeMode"
        private const val DÉFAUT_THÈME_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun sauvegarderThème(themeMode: Int) {
        val sharedPref = vue.getContext()?.getSharedPreferences(PRÉFS_NOM, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref?.edit()) {
                this?.putInt(THÈME_MODE_KEY, themeMode)
                this?.apply()
            }
        }
    }
}