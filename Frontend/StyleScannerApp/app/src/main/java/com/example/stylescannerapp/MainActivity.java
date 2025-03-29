package com.example.stylescannerapp;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.stylescannerapp.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Topaz";

    private ActivityMainBinding binding;

    private Bitmap bitmap;

    private Location mLocation;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            getLastLocation();
                        } else {
                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.image.setImageBitmap(bitmap);
                }
            });

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        Uri uri = result.getData().getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.image.setImageBitmap(bitmap);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        checkLocationPermission();

        binding.camera.setOnClickListener(view -> {
            openCamera();
        });

        binding.gallery.setOnClickListener(view -> {
            openGallery();
        });

        binding.send.setOnClickListener(v -> {
            sendPhoto();
        });

    }

    private void sendPhoto() {
        Address address = getAddressFromLocation(mLocation.getLatitude(), mLocation.getLongitude());
        Image image = new Image(bitmapToBase64(bitmap), address.getCountryName(), address.getLocality());
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.uploadImageAndGetRange(image).enqueue(new Callback<Range>() {
            @Override
            public void onResponse(Call<Range> call, Response<Range> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Range range = response.body();  // Directly get the single object
                    Log.d(TAG, "Range: " + range.toString());
                } else {
                    Log.e(TAG, "Request Failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Range> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        galleryLauncher.launch(intent);
    }

    public static Bitmap base64ToBitmap(String base64String) {
        if (base64String == null) {
            return null;
        }
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        return Base64.encodeToString(boas.toByteArray(), Base64.DEFAULT);
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.ACCESS_COARSE_LOCATION)) {
            Toast.makeText(this, "Location permission is required to get your location", Toast.LENGTH_LONG).show();
            requestPermissionLauncher.launch(permission.ACCESS_FINE_LOCATION);
        } else {
            requestPermissionLauncher.launch(permission.ACCESS_FINE_LOCATION);
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLocation = task.getResult();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Address getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            } else {
                Toast.makeText(this, "No address found for the location", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error getting address: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
}