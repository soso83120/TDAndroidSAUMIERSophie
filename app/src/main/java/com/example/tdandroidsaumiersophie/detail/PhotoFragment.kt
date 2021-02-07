package com.example.tdandroidsaumiersophie.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.FragmentPhotoBinding
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(URL)
        // on vérifie qu'il y a une photo sinon on applique une image par défaut
        if(url?.isNotEmpty() == true){
            Picasso.get().load(url).placeholder(R.drawable.ic_noavailable).into(binding.imageView3)
        }

    }

    companion object {
        fun newInstance(url: String) = PhotoFragment().apply { arguments = Bundle().apply {  putString(URL, url) } }

        const val URL = "URL"
    }
}