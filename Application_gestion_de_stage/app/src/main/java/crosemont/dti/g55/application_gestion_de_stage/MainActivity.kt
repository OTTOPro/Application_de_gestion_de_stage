package crosemont.dti.g55.application_gestion_de_stage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonneesHTTP

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var url_api = getString(R.string.URL_API)
        var url_auth = getString(R.string.URL_AUTH)
        Modèle.instance.source = SourceDeDonneesHTTP(url_api, url_auth)
    }
}