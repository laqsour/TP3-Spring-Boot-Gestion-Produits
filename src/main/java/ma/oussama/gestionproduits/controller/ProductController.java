package ma.oussama.gestionproduits.controller;

import jakarta.validation.Valid;
import ma.oussama.gestionproduits.entity.Product;
import ma.oussama.gestionproduits.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() { return "redirect:/products"; }

    @GetMapping("/products")
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("products", repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword));
        } else {
            model.addAttribute("products", repository.findAll());
        }
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "products";
    }

    @GetMapping("/products/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Ajouter un produit");
        return "product-form";
    }

    @PostMapping("/products/save")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model,
                       RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", product.getId() == null ? "Ajouter un produit" : "Modifier un produit");
            return "product-form";
        }
        repository.save(product);
        redirect.addFlashAttribute("success", "Produit enregistré avec succès.");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return repository.findById(id).map(product -> {
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Modifier un produit");
            return "product-form";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("error", "Produit introuvable.");
            return "redirect:/products";
        });
    }

    @PostMapping("/products/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            redirect.addFlashAttribute("success", "Produit supprimé.");
        } else {
            redirect.addFlashAttribute("error", "Produit introuvable.");
        }
        return "redirect:/products";
    }
}
