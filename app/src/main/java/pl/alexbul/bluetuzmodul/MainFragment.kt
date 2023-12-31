package pl.alexbul.bluetuzmodul

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import changeButtonColor
import pl.alexbul.bluetuzmodul.databinding.FragmentMainBinding
import pl.alexbul.bt_def.BluetoothConstants
import pl.alexbul.bt_def.bluetooth.BluetoothController

class MainFragment : Fragment(), BluetoothController.Listener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var bluetoothController: BluetoothController
    private lateinit var btAdapter: BluetoothAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        val pref = activity?.getSharedPreferences(
            BluetoothConstants.PREFERENCES, Context.MODE_PRIVATE
        )
        val mac = pref?.getString(BluetoothConstants.MAC, "")
        bluetoothController = BluetoothController(btAdapter)

        binding.bList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_deviceListFragment)
        }
        binding.bConnect.setOnClickListener {
            bluetoothController.connect(mac ?: "", this)
        }

        binding.imageFor.setOnClickListener {
            bluetoothController.sendMessage("1")
            binding.imageRew.isEnabled = false
            changeButtonColor(binding.imageRew, Color.GRAY)
            changeButtonColor(binding.imageFor, Color.GRAY)
            binding.tvStatus.text = "FORWARD"
        }


            binding.imageRew.setOnClickListener {
                bluetoothController.sendMessage("2")
                binding.imageFor.isEnabled = false
                changeButtonColor(binding.imageRew, Color.GRAY)
                changeButtonColor(binding.imageFor, Color.GRAY)
                binding.tvStatus.text = "REVERSE"
        }

        binding.imageStop.setOnClickListener {
            bluetoothController.sendMessage("0")
            binding.imageRew.isEnabled = true
            binding.imageFor.isEnabled = true
            changeButtonColor(binding.imageRew, R.color.green2)
            changeButtonColor(binding.imageFor, R.color.green2)
            binding.tvStatus.text = "Motoren Control"



        }
    }

    private fun initBtAdapter() {
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
    }

    override fun onReceive(message: String) {
        activity?.runOnUiThread {
            when (message) {
                BluetoothController.BLUETOOTH_CONNECTED -> {
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), R.color.red)
                    binding.bConnect.text = "Disconnect"
                }

                BluetoothController.BLUETOOTH_NO_CONNECTED -> {
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), R.color.green)
                    binding.bConnect.text = "Connect"
                }

                else -> {
                    binding.tvStatus.text = message
                }
            }
        }
    }
}