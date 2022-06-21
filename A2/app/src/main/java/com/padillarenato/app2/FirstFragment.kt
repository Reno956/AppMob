package com.padillarenato.app2

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.padillarenato.app2.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val dialogBuilder = AlertDialog.Builder(activity)
            dialogBuilder.setTitle("Confirmación de ir a siguiente fragmento")
            dialogBuilder.setMessage("¿Esta seguro que desea ir el siguiente fragmento?")
            dialogBuilder.setPositiveButton("IR", DialogInterface.OnClickListener { _, _ ->
                println("Navegando al siguiente fragmento")
                Toast.makeText(context,"Navegando al siguiente fragmento",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                //pass
            })
            dialogBuilder.create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}