document.addEventListener('DOMContentLoaded', function() {
    initDropdownMenu();
    initThemeSwitcher();
    
    if (window.location.pathname.includes('gallery.html')) {
        initImageSlider();
    }
        initFormValidation();
});

function initDropdownMenu() {
    const navbar = document.querySelector('.navbar');
    if (!navbar) return;
    
    const menuButton = document.createElement('button');
    menuButton.className = 'menu-toggle';
    menuButton.innerHTML = '☰ Меню';
    menuButton.style.cssText = `
        display: block;
        background: #333;
        color: white;
        border: none;
        padding: 10px 15px;
        border-radius: 5px;
        cursor: pointer;
        margin: 10px auto;
        font-size: 16px;
    `;
    
    navbar.parentNode.insertBefore(menuButton, navbar);
    
    const style = document.createElement('style');
    style.textContent = `
        .menu-toggle {
            display: block !important;
        }
        
        .navbar {
            display: none;
            flex-direction: column;
            position: absolute;
            top: 60px;
            left: 0;
            right: 0;
            background: #333;
            z-index: 1000;
            padding: 10px 0;
            box-shadow: 0 4px 8px rgba(0,0,0,0.3);
        }
        
        .navbar.show {
            display: flex;
            animation: slideDown 0.3s ease;
        }
        
        .nav-link {
            display: block;
            margin: 5px 0;
            text-align: center;
            padding: 12px 20px;
            transition: background-color 0.2s ease;
        }
        
        .nav-link:hover {
            background-color: #555 !important;
        }
        
        @keyframes slideDown {
            from { 
                opacity: 0; 
                transform: translateY(-20px); 
            }
            to { 
                opacity: 1; 
                transform: translateY(0); 
            }
        }
        
        @media (min-width: 769px) {
            .menu-toggle {
                margin: 10px 0;
            }
        }
    `;
    document.head.appendChild(style);
    
    menuButton.addEventListener('click', function() {
        navbar.classList.toggle('show');
        if (navbar.classList.contains('show')) {
            this.innerHTML = '✕ Закрыть';
        } else {
            this.innerHTML = '☰ Меню';
        }
    });
    
    document.addEventListener('click', function(event) {
        if (!navbar.contains(event.target) && !menuButton.contains(event.target)) {
            navbar.classList.remove('show');
            menuButton.innerHTML = '☰ Меню';
        }
    });
    
    const navLinks = navbar.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function() {
            navbar.classList.remove('show');
            menuButton.innerHTML = '☰ Меню';
        });
    });
}

// Слайдер изображений
function initImageSlider() {
    const galleryTable = document.querySelector('.gallery-table');
    if (!galleryTable) return;
    
    const images = Array.from(document.querySelectorAll('.gallery-image'));
    if (images.length === 0) return;
    
    const sliderContainer = document.createElement('div');
    sliderContainer.className = 'slider-container';
    sliderContainer.style.cssText = `
        position: relative;
        max-width: 600px;
        margin: 20px auto;
        overflow: hidden;
        border: 3px solid #333;
        border-radius: 10px;
    `;
    
    const slider = document.createElement('div');
    slider.className = 'slider';
    slider.style.cssText = `
        display: flex;
        transition: transform 0.5s ease-in-out;
    `;
    
    images.forEach((img, index) => {
        const slide = document.createElement('div');
        slide.className = 'slide';
        slide.style.cssText = `
            min-width: 100%;
            text-align: center;
        `;
        
        const slideImg = img.cloneNode(true);
        slideImg.style.cssText = `
            width: 100%;
            height: 400px;
            object-fit: cover;
        `;
        
        slide.appendChild(slideImg);
        slider.appendChild(slide);
    });
    
    const prevButton = document.createElement('button');
    prevButton.innerHTML = '◀';
    prevButton.className = 'slider-btn prev';
    prevButton.style.cssText = `
        position: absolute;
        top: 50%;
        left: 10px;
        transform: translateY(-50%);
        background: rgba(0,0,0,0.5);
        color: white;
        border: none;
        padding: 15px;
        border-radius: 50%;
        cursor: pointer;
        font-size: 20px;
    `;
    
    const nextButton = document.createElement('button');
    nextButton.innerHTML = '▶';
    nextButton.className = 'slider-btn next';
    nextButton.style.cssText = `
        position: absolute;
        top: 50%;
        right: 10px;
        transform: translateY(-50%);
        background: rgba(0,0,0,0.5);
        color: white;
        border: none;
        padding: 15px;
        border-radius: 50%;
        cursor: pointer;
        font-size: 20px;
    `;
    
    const indicators = document.createElement('div');
    indicators.className = 'slider-indicators';
    indicators.style.cssText = `
        position: absolute;
        bottom: 10px;
        left: 0;
        right: 0;
        display: flex;
        justify-content: center;
        gap: 10px;
    `;
    
    images.forEach((_, index) => {
        const indicator = document.createElement('button');
        indicator.className = 'indicator';
        indicator.dataset.index = index;
        indicator.style.cssText = `
            width: 12px;
            height: 12px;
            border-radius: 50%;
            border: none;
            background: ${index === 0 ? '#333' : 'rgba(255,255,255,0.5)'};
            cursor: pointer;
        `;
        indicators.appendChild(indicator);
    });
    
    sliderContainer.appendChild(slider);
    sliderContainer.appendChild(prevButton);
    sliderContainer.appendChild(nextButton);
    sliderContainer.appendChild(indicators);
    
    galleryTable.parentNode.replaceChild(sliderContainer, galleryTable);
    
    let currentSlide = 0;
    
    function updateSlider() {
        slider.style.transform = `translateX(-${currentSlide * 100}%)`;
        
        document.querySelectorAll('.indicator').forEach((ind, index) => {
            ind.style.background = index === currentSlide ? '#333' : 'rgba(255,255,255,0.5)';
        });
    }
    
    prevButton.addEventListener('click', () => {
        currentSlide = (currentSlide - 1 + images.length) % images.length;
        updateSlider();
    });
    
    nextButton.addEventListener('click', () => {
        currentSlide = (currentSlide + 1) % images.length;
        updateSlider();
    });
    
    document.querySelectorAll('.indicator').forEach((indicator, index) => {
        indicator.addEventListener('click', () => {
            currentSlide = index;
            updateSlider();
        });
    });
    
    let autoSlide = setInterval(() => {
        currentSlide = (currentSlide + 1) % images.length;
        updateSlider();
    }, 3000);
    
    sliderContainer.addEventListener('mouseenter', () => {
        clearInterval(autoSlide);
    });
    
    sliderContainer.addEventListener('mouseleave', () => {
        autoSlide = setInterval(() => {
            currentSlide = (currentSlide + 1) % images.length;
            updateSlider();
        }, 3000);
    });
}

// Переключение темы
function initThemeSwitcher() {
    const themeButton = document.createElement('button');
    themeButton.id = 'theme-toggle';
    themeButton.innerHTML = '🌙 Тёмная тема';
    themeButton.style.cssText = `
        position: fixed;
        top: 10px;
        right: 10px;
        padding: 10px 15px;
        background: #333;
        color: white;
        border: none;
        border-radius: 20px;
        cursor: pointer;
        z-index: 1000;
        font-size: 14px;
    `;
    
    document.body.appendChild(themeButton);
    
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
        themeButton.innerHTML = '☀️ Светлая тема';
    }
    
    themeButton.addEventListener('click', function() {
        document.body.classList.toggle('dark-theme');
        
        if (document.body.classList.contains('dark-theme')) {
            localStorage.setItem('theme', 'dark');
            this.innerHTML = ' Светлая тема';
        } else {
            localStorage.setItem('theme', 'light');
            this.innerHTML = ' Тёмная тема';
        }
    });
}

function initFormValidation() {
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!validateForm(this)) {
                event.preventDefault();
            }
        });
    });
}

function validateForm(form) {
    let isValid = true;
    const inputs = form.querySelectorAll('input[required], textarea[required]');
    
    inputs.forEach(input => {
        input.style.borderColor = '';
        const errorElement = input.nextElementSibling;
        if (errorElement && errorElement.className === 'error-message') {
            errorElement.remove();
        }
        
        if (!input.value.trim()) {
            showError(input, 'Это поле обязательно для заполнения');
            isValid = false;
            return;
        }
        
        if (input.type === 'email') {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(input.value)) {
                showError(input, 'Введите корректный email адрес');
                isValid = false;
            }
        }
        
        if (input.type === 'tel') {
            const phoneRegex = /^[\+]?[0-9\-\(\)\s]+$/;
            if (!phoneRegex.test(input.value)) {
                showError(input, 'Введите корректный номер телефона');
                isValid = false;
            }
        }
    });
    
    return isValid;
}

