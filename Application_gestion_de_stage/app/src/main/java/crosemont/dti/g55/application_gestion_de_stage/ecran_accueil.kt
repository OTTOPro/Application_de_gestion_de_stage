package crosemont.dti.g55.application_gestion_de_stage

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import crosemont.dti.g55.application_gestion_de_stage.présentation.login.IPrésentateurLogin
import crosemont.dti.g55.application_gestion_de_stage.présentation.login.IVueLogin
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.LoginPrésentateur

class ecran_accueil: IVueLogin,Fragment() {

    lateinit var présentateur : IPrésentateurLogin
    lateinit var navControlleur: NavController
    lateinit var btnConnecter : Button
    lateinit var editCourriel : EditText
    lateinit var editMotDePasse : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ecran_accueil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnConnecter = view.findViewById( R.id.btn_commencer )
        editCourriel = view.findViewById( R.id.editCourriel )
        editMotDePasse = view.findViewById( R.id.editMotDePasse )

        navControlleur = findNavController()
        présentateur = LoginPrésentateur(this)

        btnConnecter.setOnClickListener {
            présentateur.traiter_login()
        }
    }

    override fun naviguer_vers_accueil() {
        navControlleur.navigate(R.id.action_ecran_accueil_to_ecran_recherche)
    }

    override fun afficher_erreur(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun obtenir_courriel(): String {
        return editCourriel.text.toString()
    }

    override fun obtenir_mot_de_passe() : String {
        return editMotDePasse.text.toString()
    }
}