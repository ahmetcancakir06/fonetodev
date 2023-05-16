package com.example.fonet_mulakat.controller;


import com.example.fonet_mulakat.config.CreateUserRequest;
import com.example.fonet_mulakat.config.LoginRequest;
import com.example.fonet_mulakat.model.Emlak;
import com.example.fonet_mulakat.model.User;
import com.example.fonet_mulakat.repo.EmlakRepo;
import com.example.fonet_mulakat.service.CustomUserDetailsService;
import com.example.fonet_mulakat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    @Autowired
    private EmlakRepo emlakRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @CrossOrigin
    @GetMapping(value="/evler")
    public List<Emlak> getEvler(){return emlakRepo.findAll();}

    @CrossOrigin
    @GetMapping(value="/evler/{id}")
    public Emlak getEv(@PathVariable long id){
        return emlakRepo.findById(id).orElse(null);
    }


    public UserService getUserService() {
        return userService;
    }

    @PostMapping("/test")
    public String getPage(){ return "test";}
    @CrossOrigin
    @PostMapping("/kayitol")
    public User createUser(@RequestBody CreateUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();

        return userService.createUser(username, password,role);
    }
    @CrossOrigin
    @PostMapping(value="/filtre")
    public List<Emlak> getKiralik(@RequestBody Emlak filtre){
        String isinma = filtre.getIsinma();
        String kiralik = filtre.getKiralik();
        String tur = filtre.getTur();
        return emlakRepo.filtreleEmlak(isinma,kiralik,tur);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/girisyap")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // Kullanıcının kimlik doğrulamasını yap
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Kimlik doğrulaması başarılı ise oturumu başlat
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return ResponseEntity.ok("Giriş başarılı!");
    }
    @CrossOrigin
    @PostMapping(value = "/kaydet")
    public ResponseEntity<?> emlakOlustur(@RequestParam("mkare") String mkare,
                                          @RequestParam("osayi") String osayi,
                                          @RequestParam("bkati") String bkati,
                                          @RequestParam("bbkat") String bbkat,
                                          @RequestParam("isinma") String isinma,
                                          @RequestParam("ucret") String ucret,
                                          @RequestParam("tur") String tur,
                                          @RequestParam("kiralik") String kiralik,
                                          @RequestParam("foto") MultipartFile foto,
                                          @RequestParam("tam_adres") String tam_adres,
                                          @RequestParam("sehir") String sehir,
                                          @RequestParam("sokak") String sokak,
                                          @RequestParam("zip") String zip) {
        try {
            String contentType = foto.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/jpg")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Desteklenmeyen dosya türü.");
            }

            // Yüklenen dosyayı kaydetmek için gereken kodu yazın
            String fileName = StringUtils.cleanPath(foto.getOriginalFilename());
            Path path = Paths.get("C:/MAMP/htdocs/quarter/img/emlak/" + fileName);
            Files.copy(foto.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Emlak emlak = new Emlak(mkare, osayi, bkati, bbkat, isinma, ucret, tur, kiralik, "img/emlak/" +fileName, tam_adres, sehir, sokak, zip);
            emlakRepo.save(emlak);
            return ResponseEntity.ok().body("Başarılı");
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}