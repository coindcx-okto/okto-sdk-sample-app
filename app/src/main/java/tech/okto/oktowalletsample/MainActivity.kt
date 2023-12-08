package tech.okto.oktowalletsample



import android.content.ClipboardManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.widget.Toast
import tech.okto.oktowallet.OktoWallet
import tech.okto.oktowalletsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idToken = intent.getStringExtra("idToken");

        intent.extras?.getString("idToken")?.let {
            OktoWallet.authenticate(it) { result, error ->
                // wallet address of the user
                print("result: $result error: $error")
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResults.setOnClickListener {
            //copy to clipboard
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = binding.tvResults.text
        }

        setSupportActionBar(binding.toolbar)

        binding.btnLogout.setOnClickListener {
            onLogoutClick()
        }
        binding.btnGetUserDetails.setOnClickListener {
            onGetUserDetailsClick()
        }
        binding.btnGetWallets.setOnClickListener {
            onGetWalletsClick()
        }
        binding.btnWalletActivity.setOnClickListener {
            onOrderHistoryClick()
        }
        binding.btnGetSupportedTokens.setOnClickListener {
            onGetSupportedTokensClick()
        }
        binding.btnGetSupportedNetworks.setOnClickListener {
            onGetSupportedNetworksClick()
        }
        binding.btnGetPortfolio.setOnClickListener {
            onGetPortfolioClick()
        }
        binding.btnTransferFunds.setOnClickListener {
            onTransferFundsClick()
        }

        binding.btnRawExecute.setOnClickListener {
            onExecuteRawTransaction()
        }

        binding.btnFetchRawOrderStatus.setOnClickListener {
            onFetchRawOrderStatus()
        }

    }

    private fun onFetchRawOrderStatus(){
        OktoWallet.getRawTransactionStatus("") { result, error ->
            showResult(result.toString(), error)
        }
    }

    private fun onTransferFundsClick() {
        val networkName = binding.etNetworkName.text.toString().trim()
        val tokenAddress = binding.etTokenAddress.text.toString().trim()
        val recipientAddress = binding.etRecipientAddress.text.toString().trim()
        val quantity = binding.etQuantity.text.toString().trim()

        if (networkName.isEmpty()) {
            Toast.makeText(this, "Network name cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (tokenAddress.isEmpty()) {
            Toast.makeText(this, "Token address cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (recipientAddress.isEmpty()) {
            Toast.makeText(this, "Recipient address cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (quantity.isEmpty()) {
            Toast.makeText(this, "Quantity cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        OktoWallet.transferFunds(
            networkName,
            tokenAddress,
            recipientAddress,
            quantity
        ) { result, error ->
            showResult(result.toString(), error)
        }
    }

    private fun onExecuteRawTransaction() {
        val networkName = binding.rawNetwork.text.toString().trim()
        val data = binding.rawData.text.toString().trim()
        val from = binding.rawFrom.text.toString().trim()
        val to = binding.rawTo.text.toString().trim()
        val value = binding.rawValue.text.toString().trim()

        if (networkName.isEmpty()) {
            Toast.makeText(this, "Network name cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (data.isEmpty()) {
            Toast.makeText(this, "Data cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (from.isEmpty()) {
            Toast.makeText(this, "From cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        

        if (to.isEmpty()) {
            Toast.makeText(this, "To cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (value.isEmpty()) {
            Toast.makeText(this, "Value cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        OktoWallet.executeRawTransaction(
            networkName,
            data,
            from,
            to,
            value
        ) { result, error ->
            showResult(result.toString(), error)
        }
    }

    private fun onGetPortfolioClick() {
        OktoWallet.getPortfolio { result, error -> showResult(result.toString(), error) }
    }


    private fun onGetSupportedNetworksClick() {
        OktoWallet.getSupportedNetworks { result, error -> showResult(result.toString(), error) }
    }

    private fun onGetSupportedTokensClick() {
        OktoWallet.getSupportedTokens { result, error -> showResult(result.toString(), error) }
    }

    private fun onOrderHistoryClick() {
        OktoWallet.orderHistory { result, error -> showResult(result.toString(), error) }
    }

    private fun onGetWalletsClick() {
        OktoWallet.getWallets { result, error -> showResult(result.toString(), error) }
    }

    private fun onGetUserDetailsClick() {
        OktoWallet.getUserDetails { result, error -> showResult(result.toString(), error) }
    }

    private fun onLogoutClick() {
        OktoWallet.logout { result, error -> showResult(result.toString(), error) }
    }


    private fun showResult(
        result: String?,
        error: String?
    ) {
        if (error != null) {
            binding.tvResults.text = "Error: $error"
        } else {
            binding.tvResults.text = "Success: $result"
        }

    }



}