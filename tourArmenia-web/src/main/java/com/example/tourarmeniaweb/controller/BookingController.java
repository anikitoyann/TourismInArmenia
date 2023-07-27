package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.repository.BookingRepository;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RequestMapping("/booking")
@RequiredArgsConstructor
@Controller
public class BookingController {

    private final TourPackageService tourService;
    private final ItemService itemService;
    private final CarService carService;
    private final BookService bookService;
    private final MailService mailService;
    private final UserService userService;
    @Value("${site.url}")
    private String siteUrl;

    //to show the form for new booking
    @GetMapping("/createCustomTour")
    public String showBookingForm(ModelMap modelMap) {
        List<Car> cars = carService.findAll();
        List<Item> items = itemService.findAll();
        List<TourPackage> tours = tourPackageService.findAll();
        modelMap.addAttribute("tours", tours);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("booking", new Book());
        return "booking";
    }

    //If there is no existing matching tour for the user, create a custom tour for them
    @PostMapping("/createCustomTour")
    public String createBooking(@RequestParam("groupSize") int groupSize,
                                @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam("item.id") Integer itemId,
                                @RequestParam("car.id") Integer carId,
                                @RequestParam("notes") String notes,
                                @AuthenticationPrincipal CurrentUser currentUser
    ) {
        Item item = itemService.findById(itemId).get();
        Car car = carService.findById(carId).get();
        Book book = new Book();
        book.setGuestCount(groupSize);
        book.setItem(item);
        book.setCar(car);
        book.setStartDate(startDate);
        book.setNotes(notes);
        book.setUser(currentUser.getUser());
        book.setEnabled(false);
        UUID token = UUID.randomUUID();
        book.setToken(token.toString());
        bookService.save(book);
        mailService.sendMail(currentUser.getUser().getEmail(), "Last Step to an Unforgettable Experience - Validate Your Booking Now", "Hi," + currentUser.getUser().getName() + "!" + "\nCongratulations! You're one step closer to an unforgettable experience! Click on the magical link below to seal the deal and confirm your booking. Let the adventure begin!"
                + siteUrl + "/booking/verify?email=" + currentUser.getUser().getEmail() + "&token=" + token);
        return "redirect:/tour";
    }

    //to book an existing in db tour
    @PostMapping
    public String bookTour(@AuthenticationPrincipal CurrentUser currentUser,
                           @RequestParam("tourId") Integer tourId) {

        Optional<TourPackage> tour = tourService.findById(tourId);
        Book book = new Book();
        book.setTourPackage(tour.get());
        book.setGuestCount(tour.get().getGroupSize());
        book.setStartDate(tour.get().getStartDate());
        book.setItem(tour.get().getItem());
        book.setCar(tour.get().getCar());
        book.setUser(currentUser.getUser());
        book.setEnabled(false);
        UUID token = UUID.randomUUID();
        book.setToken(token.toString());
        bookService.save(book);
        mailService.sendMail(currentUser.getUser().getEmail(), "Last Step to an Unforgettable Experience - Validate Your Booking Now", "Hi," + currentUser.getUser().getName() + "!" + "\nCongratulations! You're one step closer to an unforgettable experience! Click on the magical link below to seal the deal and confirm your booking. Let the adventure begin!"
                + siteUrl + "/booking/verify?email=" + currentUser.getUser().getEmail() + "&token=" + token);

        return "redirect:/tour";
    }

    @GetMapping("/verify")
    public String verifyBooking(@RequestParam("email") String email,
                                @RequestParam("token") String token) {
        Optional<User> userByEmail = userService.findByEmail(email);
        Optional<Book> bookByUser = bookService.findByUser(userByEmail.get());
        if (bookByUser.isEmpty()) {
            return "redirect:/";
        }
        if (bookByUser.get().isEnabled()) {
            return "redirect:/";
        }
        if (bookByUser.get().getToken().equals(token)) {
            Book book = bookByUser.get();
            book.setEnabled(true);
            book.setToken(null);
            bookService.save(book);
        }
        return "verified";
    }

    @GetMapping("/booking")
    public String bookTour(@RequestParam("tourId") Integer tourId,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        return "redirect:/tour";

    }

    @GetMapping("/bookings")
    public String showBookings(ModelMap modelMap) {
        List<Book> all = bookService.findAll();
        modelMap.addAttribute("bookings", all);
        return "bookings";
    }
}

