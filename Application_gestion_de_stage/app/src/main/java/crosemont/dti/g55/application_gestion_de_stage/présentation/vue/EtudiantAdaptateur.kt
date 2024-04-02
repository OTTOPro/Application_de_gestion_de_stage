package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import crosemont.dti.g55.application_gestion_de_stage.R
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.ecran_profil_etudiant

class EtudiantAdaptateur(private var etudiants: List<Etudiant>, private val startForResult: ActivityResultLauncher<Intent>): RecyclerView.Adapter<EtudiantAdaptateur.EtudiantViewHolder>() {

    private var selectedImageUri: Uri? = null
    private var imageBitmap: Bitmap? = null
    private var avatar: String? = null

    inner class EtudiantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nomEtudiantTextView: TextView = itemView.findViewById(R.id.nomPrenomEtudiant)
        val courrielTextView: TextView = itemView.findViewById(R.id.courriel_etudiant)
        val telephoneTextView: TextView = itemView.findViewById(R.id.telephone_etudiant)
        val etatStageTextView: TextView = itemView.findViewById(R.id.etat_stage_etudiant)
        val adresseTextView: TextView = itemView.findViewById(R.id.adresse_etudiant)
        val languesTextView: TextView = itemView.findViewById(R.id.langues_etudiant)
        val transportTextView: TextView = itemView.findViewById(R.id.transport_etudiant)
        val codeTextView: TextView = itemView.findViewById(R.id.profil_et_code_etudiant)
        val imageEtudiant: ImageView = itemView.findViewById(R.id.photo_profil)
        val btnPhotoEtudiant: ImageButton = itemView.findViewById(R.id.btnPhotoProfil)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantAdaptateur.EtudiantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profil, parent, false)
        return EtudiantViewHolder(view)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {

        val etudiant = etudiants[position]
        holder.nomEtudiantTextView.text = etudiant.prénomUtilisateur + " " + etudiant.nomUtilisateur
        holder.courrielTextView.text = etudiant.courrielUtilisateur
        holder.telephoneTextView.text = etudiant.téléphoneUtilisateur
        holder.etatStageTextView.text = "En cours de recherche"
        holder.adresseTextView.text = etudiant.adresseÉtudiant
        holder.languesTextView.text = "Francais"
        holder.transportTextView.text = "Voiture"
        holder.codeTextView.text = etudiant.codeUtilisateur
        /*val imageResourceNom = "othmane_photo"
        val context = holder.itemView.context
        val imageResource = context.resources.getIdentifier(imageResourceNom, "drawable", context.packageName)
        holder.imageEtudiant.setImageResource(imageResource)*/
        Picasso.get().load(avatar).into(holder.imageEtudiant)
        holder.btnPhotoEtudiant.setOnClickListener {

            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val chooserIntent = Intent.createChooser(galleryIntent, "Choisissez une source")

            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

            startForResult.launch(chooserIntent)
        }

        if (selectedImageUri != null) {
            holder.imageEtudiant.setImageURI(selectedImageUri)
        } else if (imageBitmap != null) {
            holder.imageEtudiant.setImageBitmap(imageBitmap)
        }

    }

    fun setImageUri(uri: Uri?) {
        selectedImageUri = uri
        notifyItemChanged(0)
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        imageBitmap = bitmap
        notifyItemChanged(0)
    }

    fun setEtudiants(etudiants: List<Etudiant>) {
        this.etudiants = etudiants
        notifyDataSetChanged()
    }

    fun setAvatar(avatarUrl: String){
        this.avatar = avatarUrl
    }

    override fun getItemCount(): Int {
        return etudiants.size
    }
}