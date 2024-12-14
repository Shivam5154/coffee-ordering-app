import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CoffeeOrderForm() {
    // the variables I chose, theyre simple and explainable
    var userName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var coffeeDrink by remember { mutableStateOf("Latte") }
    var quantity by remember { mutableStateOf("1") }

    // the drinks I chose
    val drinks = listOf("Latte", "Macchiato", "Cappuccino", "Americano")
    val prices = mapOf("Latte" to 4.00, "Macchiato" to 5.50, "Cappuccino" to 5.00, "Americano" to 3.00)
    // this is how i calculayted total price , was going to use try / catch but it was too difficult
    val totalPrice = (quantity.toIntOrNull()?.takeIf { it > 0 } ?: 1) * (prices[coffeeDrink] ?: 0.0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Coffee Shop Menu",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Enter your phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Select Your Coffee Drink of Choice:", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        drinks.forEach { drink ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (coffeeDrink == drink),
                    onClick = { coffeeDrink = drink },
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                )
                Text(text = "$drink ($${prices[drink]?.toString() ?: "0.00"})", modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "You Owe: $${"%.2f".format(totalPrice)}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                println("Order placed for $userName ($phoneNumber): $quantity x $coffeeDrink at $${"%.2f".format(totalPrice)}")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit Order", color = Color.White)
        }
    }
}
@Composable
fun CoffeeOrderApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            CoffeeOrderForm()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeOrderApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeOrderFormPreview() {
    CoffeeOrderApp()
}
