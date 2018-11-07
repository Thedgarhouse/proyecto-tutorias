package com.example.edgar.asesoriasinformales;

import android.net.Uri;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 06-Nov-18 with the Project Name: AsesoriasInformales.
 */
public class Teacher_ActivityTest {

    @Test
    public void uriToStringTest() throws Exception{
        String photoUriString = "lh6.googleusercontent.com/-Lz0-OQ-iNFw/AAAAAAAAAAI/AAAAAAAAAA0/2w4qO8szh4Q/photo.jpg";
        Uri photoUri = Uri.parse(photoUriString);
        assertEquals("Uri is wrong", "lh6.googleusercontent.com/-Lz0-OQ-iNFw/AAAAAAAAAAI/AAAAAAAAAA0/2w4qO8szh4Q/photo.jpg", photoUri.toString());
    }
}