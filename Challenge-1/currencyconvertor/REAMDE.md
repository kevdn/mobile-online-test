# Currency Converter App

This Android Currency Converter app allows users to convert currencies and save preferred currency pairs for quick reference. It fetches live exchange rates via the ExchangeRate API and features an intuitive UI for input and display of currency conversions.

## App Structure

```bash

├── main
│   ├── AndroidManifest.xml
│   ├── assets
│   │   └── common-currency.json
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── currency_convertor
│   │               ├── api
│   │               │   └── ApiService.kt
│   │               ├── CurrencyConverterApp.kt
│   │               ├── data
│   │               │   ├── local
│   │               │   │   └── CurrencyRate.kt
│   │               │   ├── model
│   │               │   │   ├── ConversionResponse.kt
│   │               │   │   ├── Currency.kt
│   │               │   │   └── CurrencyRateResponse.kt
│   │               │   └── repository
│   │               │       └── CurrencyRepository.kt
│   │               ├── di
│   │               │   └── AppModule.kt
│   │               ├── MainActivity.kt
│   │               ├── ui
│   │               │   ├── convert
│   │               │   │   ├── ConvertFragment.kt
│   │               │   │   └── ConvertViewModel.kt
│   │               │   └── dashboard
│   │               │       ├── DashboardFragment.kt
│   │               │       └── DashboardViewModel.kt
│   │               └── utils
│   │                   ├── Constants.kt
│   │                   └── CurrencyUtils.kt

```

### Key Directory Breakdown

- **`ui/`**: Contains `ConvertFragment` and `DashboardFragment` for currency conversion and rate display, alongside corresponding ViewModel classes for managing data and business logic.
- **`data/local/`**: Contains `CurrencyRate` (entity) responsible for managing local data persistence.
- **`data/model/`**: Defines the `Currency` data model, mapping to the structure of currencies in the app. Also includes api response models.
- **`api/`**: Defines `ApiService`, the Retrofit interface for API communication, handling endpoint definitions and API requests.
- **`utils/`**: Includes `Constants.kt` for static values like API keys, and `CurrencyUtils.kt` for helper functions, e.g., loading currency data from JSON.


## Building and Running the App

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/kevdn/mobile-online-test
   cd Challenge-1/CurrencyConverterApp
   
2. **Set up API Key**:
   - Create an account on [ExchangeRate-API](https://www.exchangerate-api.com/).
   - Copy your API key from the dashboard.
   - Open `Constants.kt` and replace `"YOUR_API_KEY"` with your actual API key.
3. **Install the Dependencies**:
   ```bash
   ./gradlew build
   ```
4. **Run the App**:

-- Run the app on an emulator or connected device using Android Studio.

5. **Build the APK**:
   ```bash
   ./gradlew assembleDebug
   ```
   You can find the APK at `app/build/outputs/apk/debug/app-debug.apk`.
 
## Additional Notes

- **Error Handling**:
    - Network and API-related errors, such as unsupported currency codes or quota limits, are displayed as user-friendly messages in the UI.
    - Validation ensures that the currency selection and amount fields are populated to avoid 404 errors from the API.

- **Challenges**:
    - **Dropdown Management**: Implementing searchable dropdowns for both currency fields required integrating data loading and adapter management carefully for smooth user interaction.
    - **API Error Management**: The API's limitations, such as unsupported codes, required detailed handling to ensure smooth user experience without crashes.

## Link to demo video

[Currency Converter App Demo](https://drive.google.com/file/d/1uDNsmSoqhENJeKrDd7OZjAs8uWJfSs0h/view?usp=sharing)