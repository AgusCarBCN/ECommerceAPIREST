-- ==============================================
-- CATEGORIES
-- ==============================================
-- Insertar categorías basadas en los productos anteriores
INSERT INTO categories (category, description)
VALUES ('MONITORS',
        'Computer displays including gaming, professional, and general use monitors with various sizes and resolutions'),
       ('HOME_COMPUTERS',
        'Desktop computers designed for general home use, everyday tasks, and family computing needs'),
       ('GAMING_COMPUTERS',
        'High-performance desktop systems optimized for gaming with powerful graphics and cooling solutions'),
       ('PROFESSIONAL_WORKSTATIONS',
        'Premium computers for professional use including content creation, engineering, and scientific applications'),
       ('MICE', 'Computer mice including gaming mice, ergonomic designs, wireless and wired options'),
       ('KEYBOARDS', 'Computer keyboards including mechanical, membrane, wireless, and ergonomic models'),
       ('HEADSETS', 'Audio headsets including gaming headsets, professional headphones, and wireless earbuds'),
       ('PC_COMPONENTS', 'Internal computer components and upgrades'),
       ('STORAGE_DEVICES', 'Hard drives, SSDs, and external storage solutions'),
       ('NETWORKING_EQUIPMENT', 'Routers, switches, network adapters and other networking hardware');

-- Si necesitas una estructura jerárquica, puedes agregar una columna parent_id a la tabla categories
-- y luego insertar subcategorías como:

/*
ALTER TABLE categories ADD COLUMN parent_id UUID REFERENCES categories(id);

-- Primero insertar categorías principales
INSERT INTO categories (name, description) VALUES
('Computers', 'Complete computer systems and workstations'),
('Peripherals', 'External computer devices and accessories'),
('Components', 'Internal computer parts and hardware');

-- Luego insertar subcategorías referenciando al parent_id
-- (Asumiendo que obtienes los IDs de las categorías principales)
*/


INSERT INTO products_catalog (product_name, description, price, stock_quantity)
VALUES
-- Monitores (59 productos)
('Dell UltraSharp 27" Monitor', 'Professional monitor with 4K resolution and color calibration for designers', 599.99, 45),
('ASUS ROG Swift 27" Gaming Monitor', 'High-performance gaming monitor with 240Hz refresh rate and G-Sync', 699.99, 32),
('LG 34" Ultrawide Curved Monitor', 'Ultrawide curved monitor with HDR support for immersive experience', 799.99, 28),
('Samsung Odyssey G7 32" Monitor', 'Gaming monitor with 1000R curvature and QLED technology', 649.99, 37),
('HP 24" Business Monitor', 'Affordable business monitor with eye comfort features', 189.99, 65),
('Acer Predator 27" Monitor', 'Gaming monitor with 144Hz refresh rate and NVIDIA G-Sync', 499.99, 41),
('BenQ DesignVue 27" Monitor', 'Color-accurate monitor for professional photo and video editing', 529.99, 26),
('ViewSonic 32" 4K Monitor', 'Large 4K UHD monitor with HDMI and DisplayPort inputs', 429.99, 38),
('MSI Optix 27" Curved Monitor', 'Curved gaming monitor with 165Hz refresh rate and wide color gamut', 389.99, 44),
('Apple Pro Display XDR', 'Professional reference monitor with extreme dynamic range', 4999.99, 8),
('Dell S2721DS 27" Monitor', 'QHD monitor with thin bezels and adjustable stand', 319.99, 52),
('ASUS TUF Gaming 32" Monitor', 'Durable gaming monitor with ELMB sync and Shadow Boost', 349.99, 39),
('LG 27" 4K UHD Monitor', '27-inch 4K monitor with HDR10 and USB-C connectivity', 379.99, 47),
('Samsung 49" Super Ultrawide Monitor', 'Dual QHD super ultrawide monitor for multitasking', 1199.99, 15),
('Acer Nitro 23.8" Monitor', 'Gaming monitor with AMD FreeSync and 1ms response time', 229.99, 58),
('ViewSonic Elite 35" Curved Monitor', 'Ultrawide curved gaming monitor with RGB lighting', 899.99, 22),
('BenQ GW2780 27" Monitor', 'Eye-care monitor with brightness intelligence technology', 199.99, 61),
('HP 27" QHD Monitor', 'Quad HD monitor with USB hub and height adjustment', 299.99, 49),
('ASUS ProArt 32" Monitor', 'Professional monitor with Calman Verified color accuracy', 899.99, 19),
('MSI Modern 24" Monitor', 'Modern design monitor with thin bezels and 75Hz refresh rate', 159.99, 67),
('Dell 24" Gaming Monitor', 'Full HD gaming monitor with 144Hz refresh rate', 219.99, 53),
('LG UltraGear 27" Monitor', 'Nano IPS gaming monitor with 1ms response time', 449.99, 36),
('Samsung 28" 4K UHD Monitor', '4K UHD monitor with AMD FreeSync and HDR', 329.99, 42),
('Acer 31.5" Curved Monitor', 'Curved VA monitor with 1500R curvature', 279.99, 48),
('ASUS VG279Q 27" Monitor', 'Gaming monitor with IPS panel and adaptive sync', 299.99, 41),
('ViewSonic 24" Gaming Monitor', 'Esports gaming monitor with 1ms response time', 249.99, 55),
('BenQ Zowie XL2546 25" Monitor', 'Competitive gaming monitor with DyAc technology', 499.99, 29),
('HP 27" 4K Monitor', '4K UHD monitor with HDR and USB-C docking', 429.99, 38),
('MSI MAG274QRF 27" Monitor', 'Rapid IPS gaming monitor with quantum dot technology', 569.99, 33),
('Dell UltraSharp 32" 4K Monitor', 'PremierColor monitor with 4K UHD resolution', 849.99, 24),
('LG 38" Curved UltraWide Monitor', 'Curved ultra-wide monitor with Nano IPS display', 1499.99, 12),
('Samsung 27" Odyssey G5 Monitor', '1000R curved gaming monitor with WQHD resolution', 349.99, 46),
('ASUS ROG Strix 35" Monitor', 'Ultrawide curved gaming monitor with 200Hz refresh rate', 1099.99, 18),
('Acer 34" Curved UltraWide Monitor', 'Ultrawide curved monitor with 144Hz refresh rate', 799.99, 27),
('ViewSonic 27" 4K Monitor', '4K UHD monitor with HDR and 100% sRGB coverage', 399.99, 43),
('BenQ PD3220U 32" Monitor', 'Thunderbolt 3 monitor for creative professionals', 1299.99, 16),
('HP Z27 27" 4K Monitor', 'DreamColor monitor for professional color work', 899.99, 21),
('MSI Prestige PS341WU 34" Monitor', 'Ultrawide 5K2K monitor for content creators', 1399.99, 14),
('Dell 27" USB-C Monitor', 'USB-C hub monitor with power delivery and ethernet', 479.99, 39),
('LG 27" Ergo Monitor', 'Monitor with ergonomic stand and USB-C connectivity', 349.99, 47),
('Samsung UR55 28" Monitor', '4K UHD monitor with AMD FreeSync support', 299.99, 51),
('ASUS VA32UQ 31.5" Monitor', '4K UHD monitor with 95% DCI-P3 color gamut', 479.99, 35),
('Acer CB282K 28" Monitor', '4K UHD monitor with HDR400 and 90% DCI-P3', 379.99, 44),
('ViewSonic VP2756-4K 27" Monitor', '4K monitor with hardware calibration', 529.99, 31),
('BenQ EX2780Q 27" Monitor', 'QHD monitor with HDRi and treVolo speakers', 499.99, 37),
('HP E24 G4 23.8" Monitor', 'Business monitor with low blue light technology', 189.99, 62),
('MSI G32C4 32" Monitor', 'Curved gaming monitor with 165Hz refresh rate', 299.99, 52),
('Dell P3222QE 32" Monitor', '4K UHD monitor with USB-C connectivity', 679.99, 29),
('LG 27QN880-B 27" Monitor', 'Ergo dual QHD monitor with USB-C docking', 449.99, 41),
('Samsung S39C 34" Monitor', 'Ultrawide curved monitor with 100Hz refresh rate', 349.99, 56),
('ASUS BE279QSK 27" Monitor', '4K UHD business monitor with webcam', 529.99, 34),
('Acer ED323QUR 31.5" Monitor', 'Curved WQHD monitor with 165Hz refresh rate', 329.99, 48),
('ViewSonic XG2705 27" Monitor', 'IPS gaming monitor with 144Hz refresh rate', 289.99, 53),
('BenQ BL2485TC 23.8" Monitor', 'Eye-care monitor with USB-C docking station', 259.99, 59),
('HP X27 27" Monitor', 'QHD gaming monitor with 165Hz refresh rate', 399.99, 46),
('MSI Optix G273QF 27" Monitor', 'WQHD gaming monitor with 165Hz refresh rate', 429.99, 42),
('Dell S3221QS 32" Monitor', 'Curved 4K UHD monitor with AMD FreeSync', 449.99, 38),
('LG 27UL850-W 27" Monitor', '4K UHD monitor with HDR400 and USB-C', 499.99, 36),
('Samsung UJ59 32" Monitor', '4K UHD monitor for productivity and entertainment', 329.99, 54),

-- PC Home (40 productos)
('HP Pavilion Home Desktop', 'Home computer with Intel Core i5 and 512GB SSD for daily tasks', 649.99, 38),
('Dell Inspiron Home PC', 'Reliable home desktop with AMD Ryzen 5 and 1TB HDD', 579.99, 42),
('Lenovo IdeaCentre Home Desktop', 'Compact home computer with Intel Core i3 and 256GB SSD', 449.99, 51),
('Acer Aspire TC Home PC', 'Affordable home desktop with Intel Pentium and 8GB RAM', 399.99, 56),
('ASUS M515 Home Desktop', 'Home computer with Intel Core i5 and NVIDIA graphics', 729.99, 34),
('HP Slim Desktop Home PC', 'Space-saving home desktop with Intel Core i7 and 1TB SSD', 899.99, 28),
('Dell XPS Home Desktop', 'Premium home computer with Intel Core i9 and 32GB RAM', 1499.99, 19),
('Lenovo ThinkCentre Home PC', 'Business-grade home desktop with Intel Core i5 and TPM', 699.99, 41),
('Acer Veriton Home Desktop', 'Home office computer with Intel Core i5 and dual monitor support', 649.99, 39),
('ASUS ExpertCenter Home PC', 'Home computer with Intel Core i3 and quiet operation', 529.99, 47),
('HP Envy Home Desktop', 'Stylish home computer with Intel Core i7 and 512GB SSD', 999.99, 26),
('Dell OptiPlex Home PC', 'Compact home desktop with Intel Core i5 and Wi-Fi 6', 749.99, 36),
('Lenovo Legion Home Desktop', 'Home computer with gaming capabilities and RGB lighting', 849.99, 31),
('Acer Predator Home PC', 'Home desktop with powerful cooling and overclocking support', 1099.99, 23),
('ASUS ROG Strix Home Desktop', 'Home gaming computer with liquid cooling and premium components', 1299.99, 21),
('HP Omen Home PC', 'Home desktop with VR-ready graphics and customizable lighting', 1199.99, 24),
('Dell Alienware Home Desktop', 'High-end home computer with extraterrestrial design', 1799.99, 16),
('Lenovo Yoga Home Desktop', 'Versatile home computer with touch screen and flexible design', 799.99, 33),
('Acer Swift Home PC', 'Home desktop with fast boot times and energy efficiency', 599.99, 44),
('ASUS VivoPC Home Desktop', 'Compact home computer with 4K support and Harman Kardon audio', 679.99, 38),
('HP ProDesk Home PC', 'Home desktop with security features and manageability', 649.99, 40),
('Dell Precision Home Workstation', 'Home computer for creators with professional graphics', 1899.99, 14),
('Lenovo ThinkStation Home PC', 'Home workstation with ECC memory and Xeon processor', 2199.99, 11),
('Acer ConceptD Home Desktop', 'Home computer for designers with Pantone validation', 1599.99, 18),
('ASUS ProArt Home PC', 'Home desktop for creatives with Calman verification', 1399.99, 20),
('HP Z2 Home Workstation', 'Home computer with ISV certification and RAID support', 1699.99, 17),
('Dell Vostro Home Desktop', 'Home business computer with Windows 11 Pro', 799.99, 35),
('Lenovo IdeaPad Home Desktop', 'Home computer with all-in-one design and built-in speakers', 699.99, 39),
('Acer Chromebox Home PC', 'Home desktop running Chrome OS for simplicity and speed', 299.99, 62),
('ASUS Chromebox Home Desktop', 'Compact home computer with Google integration', 329.99, 58),
('HP Chromebox Home PC', 'Home desktop with automatic updates and virus protection', 349.99, 55),
('Dell Latitude Home Desktop', 'Home business computer with vPro technology', 899.99, 32),
('Lenovo Flex Home PC', 'Home desktop with 2-in-1 functionality and digital pen', 749.99, 37),
('Acer Spin Home Desktop', 'Home computer with rotating hinge and touch screen', 799.99, 34),
('ASUS Zen AiO Home PC', 'All-in-one home computer with 4K display and soundbar', 1299.99, 22),
('HP All-in-One Home Desktop', 'Home computer with integrated display and webcam', 899.99, 29),
('Dell Inspiron All-in-One Home PC', 'Space-saving home desktop with wireless keyboard and mouse', 799.99, 36),
('Lenovo IdeaCentre AIO Home Desktop', 'All-in-one home computer with pop-up webcam', 949.99, 27),
('Acer Aspire All-in-One Home PC', 'Home desktop with FHD display and stereo speakers', 699.99, 41),
('ASUS M509 Home Desktop', 'Home computer with DVD drive and multiple USB ports', 599.99, 46),

-- PCs Gaming (40 productos)
('Alienware Aurora Gaming PC', 'High-performance gaming computer with liquid cooling and RGB lighting', 2199.99, 22),
('HP Omen Gaming Desktop', 'Gaming PC with NVIDIA RTX graphics and customizable components', 1799.99, 28),
('ASUS ROG Strix Gaming PC', 'Gaming computer with Intel Core i9 and triple-fan cooling', 2499.99, 18),
('MSI MPG Gaming Desktop', 'Gaming PC with Mystic Light RGB and gaming center software', 1699.99, 31),
('CyberPowerPC Gaming Desktop', 'Gaming computer with AMD Ryzen 9 and high-speed memory', 1599.99, 34),
('SkyTech Gaming PC', 'Gaming desktop with tempered glass side panel and 240mm AIO cooler', 1399.99, 37),
('iBUYPOWER Gaming Desktop', 'Gaming PC with custom wiring and professional cable management', 1299.99, 41),
('Corsair One Gaming PC', 'Compact gaming computer with dual-chamber cooling', 2299.99, 24),
('Origin PC Gaming Desktop', 'Custom gaming computer with hand-built components and testing', 2999.99, 12),
('Maingear Vybe Gaming PC', 'Gaming desktop with premium materials and custom paint options', 1899.99, 27),
('Digital Storm Gaming Desktop', 'Gaming computer with LN2 overclocking and custom loop options', 2799.99, 15),
('Falcon Northwest Gaming PC', 'Premium gaming computer with meticulous assembly and tuning', 3499.99, 10),
('Xidax Gaming Desktop', 'Gaming PC with lifetime warranty and upgrade program', 1999.99, 26),
('Velocity Micro Gaming PC', 'Gaming computer with air or liquid cooling options', 1699.99, 32),
('ABS Gaming Desktop', 'Gaming PC with pre-tested components and quality assurance', 1499.99, 36),
('CLX Gaming PC', 'Gaming desktop with esports optimization and streaming capabilities', 1799.99, 29),
('Acer Predator Orion Gaming Desktop', 'Gaming computer with FrostBlade cooling and Tobii eye tracking', 2299.99, 23),
('Lenovo Legion Gaming PC', 'Gaming desktop with Coldfront cooling and ARGB lighting', 1699.99, 33),
('HP Victus Gaming Desktop', 'Entry-level gaming computer with modern design and performance', 999.99, 45),
('Dell G5 Gaming PC', 'Gaming desktop with Alienware technology at mainstream price', 1199.99, 39),
('ASUS TUF Gaming Desktop', 'Durable gaming computer with military-grade components', 1399.99, 38),
('MSI MAG Gaming PC', 'Gaming desktop with mesh front panel for optimal airflow', 1299.99, 42),
('CyberPowerPC Gamer Xtreme', 'VR-ready gaming computer with included keyboard and mouse', 1099.99, 47),
('SkyTech Archangel Gaming PC', 'Gaming desktop with 1TB NVMe SSD and Windows 11 Home', 1249.99, 40),
('iBUYPOWER Trace Gaming Desktop', 'Gaming computer with tempered glass and addressable RGB', 1349.99, 37),
('Corsair Vengeance Gaming PC', 'Gaming desktop with CORSAIR iCUE software integration', 1899.99, 28),
('Origin Neuron Gaming Desktop', 'Custom gaming computer with premium overclocking', 2499.99, 20),
('Maingear F131 Gaming PC', 'Gaming desktop with professional cable management and testing', 2999.99, 16),
('Digital Storm Aventum Gaming PC', 'Extreme gaming computer with 3D vapor chamber cooling', 4999.99, 8),
('Falcon Northwest Talon Gaming PC', 'Compact gaming desktop with full-sized performance', 2799.99, 18),
('Xidax X-5 Gaming Desktop', 'Gaming PC with custom engraving and signature series options', 2199.99, 25),
('Velocity Micro Raptor Gaming PC', 'Gaming computer with hand-selected components and burn-in testing', 1899.99, 30),
('ABS Gladiator Gaming Desktop', 'Gaming PC with high airflow chassis and multiple fan options', 1599.99, 35),
('CLX Ra Gaming PC', 'Gaming desktop with Egyptian-themed design and premium cooling', 2099.99, 26),
('Acer Nitro Gaming Desktop', 'Affordable gaming computer with NitroSense software', 899.99, 52),
('Lenovo IdeaCentre Gaming PC', 'Gaming desktop with 12th Gen Intel processor and WiFi 6', 1299.99, 41),
('HP Pavilion Gaming Desktop', 'Mainstream gaming computer with OMEN command center', 949.99, 49),
('Dell Inspiron Gaming PC', 'Gaming desktop with AMD Radeon graphics and dual storage', 1099.99, 44),
('ASUS PN53 Gaming Mini PC', 'Compact gaming computer with AMD Ryzen and Radeon graphics', 899.99, 53),
('MSI Cubi Gaming Desktop', 'Mini gaming PC with Intel Iris Xe graphics and quiet operation', 799.99, 57),

-- PC Profesionales (40 productos)
('Dell Precision Workstation', 'Professional computer with Intel Xeon and NVIDIA Quadro graphics', 2499.99, 21),
('HP Z4 Workstation', 'Professional desktop with ECC memory and ISV certification', 2199.99, 24),
('Lenovo ThinkStation Workstation', 'Professional computer with triple-channel memory and tool-less design', 2399.99, 22),
('Apple Mac Studio', 'Professional desktop with Apple M1 Ultra and unified memory', 3999.99, 14),
('ASUS ProArt Station', 'Professional computer for creators with studio-grade components', 1899.99, 29),
('MSI Creator P100X', 'Professional desktop with quiet operation and creator center', 2299.99, 25),
('Acer ConceptD Workstation', 'Professional computer with Pantone validation and vortex flow cooling', 2799.99, 19),
('HP Envy All-in-One Professional PC', 'Professional all-in-one desktop with 4K display and privacy camera', 1799.99, 31),
('Dell OptiPlex Professional Desktop', 'Professional business computer with manageability features', 999.99, 42),
('Lenovo ThinkCentre Professional PC', 'Professional desktop with ThinkShield security and self-healing BIOS', 1199.99,38),
('Apple Mac Mini Professional', 'Professional compact desktop with Apple M2 Pro chip', 1299.99, 36),
('ASUS ExpertCenter Professional Desktop', 'Professional computer with business reliability and expansion options', 1099.99, 40),
('MSI Modern Professional PC', 'Professional desktop with minimalist design and USB-C connectivity', 899.99, 47),
('Acer Veriton Professional Desktop', 'Professional business computer with vPro technology', 1249.99, 39),
('HP ProDesk Professional PC', 'Professional desktop with tamper lock and asset protection', 849.99, 50),
('Dell Latitude Professional Desktop', 'Professional business computer with smart card reader', 1399.99, 34),
('Lenovo IdeaPad Professional PC', 'Professional desktop with hybrid work capabilities', 949.99, 45),
('ASUS Chromebox Professional Desktop', 'Professional computer with Chrome Enterprise upgrade', 499.99, 62),
('MSI PRO Professional Desktop', 'Professional business computer with TPM 2.0 and Windows 11 Pro', 799.99, 52),
('Acer TravelMate Professional PC', 'Professional desktop with military-grade durability', 1349.99, 37),
('HP EliteDesk Professional Workstation', 'Professional computer with HP Sure Start and HP Sure Click', 1599.99, 33),
('Dell Precision Mobile Workstation', 'Professional desktop workstation with RAID support', 2899.99, 20),
('Lenovo ThinkPad Professional PC', 'Professional desktop with ThinkPad reliability and TrackPoint', 1499.99, 35),
('Apple iMac Professional', 'Professional all-in-one with Retina 5K display and studio microphone', 2299.99, 28),
('ASUS Zen AiO Pro', 'Professional all-in-one desktop with 4K touchscreen and pen support', 1999.99, 30),
('MSI Summit Professional Desktop', 'Professional computer for executives with premium materials', 1699.99, 32),
('Acer Swift Professional PC', 'Professional desktop with fast charging and biometric security', 1149.99, 41),
('HP Z1 Professional Workstation', 'Professional all-in-one workstation with Xeon processor', 2499.99, 23),
('Dell Canvas Professional Desktop', 'Professional computer with 27QHD interactive pen display', 1799.99, 31),
('Lenovo Yoga Professional PC', 'Professional desktop with rotating hinge and digital pen', 1399.99, 36),
('ASUS VivoMini Professional PC', 'Professional compact desktop with multiple mounting options', 699.99, 55),
('MSI Cubi Professional Desktop', 'Professional mini PC with VESA mount and Kensington lock', 649.99, 58),
('Acer Chromebox Enterprise', 'Professional desktop with Chrome Enterprise and extended support', 599.99, 61),
('HP Chromebox Professional PC', 'Professional computer with Chrome OS and zero-touch enrollment', 549.99, 63),
('Dell OptiPlex Micro Professional', 'Professional ultra-compact desktop with flexible deployment', 749.99, 53),
('Lenovo ThinkCentre Tiny Professional', 'Professional tiny desktop with extensive connectivity', 899.99, 48),
('ASUS PN Series Professional PC', 'Professional mini desktop with rich I/O and quiet operation', 779.99, 51),
('MSI MAG Professional Desktop', 'Professional computer with gaming-inspired cooling for sustained loads', 1199.99, 43),
('Acer Aspire TC Professional', 'Professional desktop with ample storage and expansion slots', 949.99, 46),
('HP Pavilion Professional PC', 'Professional desktop with sleek design and modern connectivity', 1049.99, 44),

-- Periféricos - Ratones (30 productos)
('Logitech MX Master 3 Mouse', 'Wireless mouse with ergonomic design and precision tracking', 99.99, 78),
('Razer DeathAdder Elite Mouse', 'Gaming mouse with optical sensor and customizable buttons', 69.99, 92),
('SteelSeries Rival 600 Mouse', 'Gaming peripheral with dual sensor system and weight tuning', 79.99, 85),
('Corsair Dark Core RGB Mouse', 'Wireless gaming mouse with Qi charging and 9 programmable buttons', 89.99, 81),
('Microsoft Surface Precision Mouse', 'Professional peripheral with multi-device pairing and comfortable grip', 99.99, 76),
('Apple Magic Mouse', 'Wireless mouse with multi-touch surface and rechargeable battery', 79.99, 88),
('ASUS ROG Gladius III Mouse', 'Gaming peripheral with hot-swap switches and 19000 DPI sensor', 89.99, 83),
('HP Z3700 Wireless Mouse', 'Affordable wireless mouse with nano receiver and 12-month battery', 19.99, 124),
('Dell MS116 Wireless Mouse', 'Basic optical mouse with plug-and-play operation', 9.99, 156),
('Logitech G502 Hero Mouse', 'Gaming peripheral with HERO sensor and 11 programmable buttons', 79.99, 90),
('Razer Naga Pro Mouse', 'Wireless gaming mouse with interchangeable side plates', 149.99, 62),
('SteelSeries Aerox 5 Mouse', 'Wireless gaming peripheral with ultra-lightweight design', 129.99, 68),
('Corsair Scimitar RGB Elite Mouse', 'MMO gaming mouse with customizable keypad and optical sensor', 79.99, 87),
('Microsoft Classic IntelliMouse', 'Mouse Updated classic peripheral with modern sensor and scroll wheel', 39.99, 105),
('Apple Magic Trackpad mouse', 'Wireless Mouse trackpad with Force Touch and multi-touch gestures', 129.99, 71),
('ASUS TUF M3 Mouse', 'Durable gaming peripheral with military-grade certification', 29.99, 112),
('HP Omen Vector Mouse', 'Gaming peripheral with magnetic charging dock and 26000 DPI', 99.99, 79),
('Dell WM126 Wireless Mouse', 'Wireless mouse with 2.4GHz connectivity and energy saving', 14.99, 138),
('Logitech M325 Wireless Mouse', 'Compact wireless mouse with precise scrolling and variety of colors', 24.99, 119),
('Razer Basilisk Ultimate Mouse', 'Wireless gaming peripheral with charging dock and 11 buttons', 169.99, 58),
('SteelSeries Sensei Ten Mouse', 'Ambidextrous gaming peripheral with TrueMove Pro sensor', 69.99, 94),
('Corsair Katar Pro Mouse', 'Lightweight gaming peripheral with contoured shape and 12400 DPI', 29.99, 115),
('Microsoft Mobile Mouse', 'Compact wireless peripheral with Bluetooth and USB-C charging', 39.99, 107),
('Apple Magic Mouse 2', 'Rechargeable wireless mouse with multi-touch surface', 99.99, 82),
('ASUS ROG Chakram Mouse', 'Gaming peripheral with joystick and Qi wireless charging', 149.99, 64),
('HP Pavilion Wireless Mouse', 'Stylish wireless mouse with silent clicks and adjustable DPI', 29.99, 113),
('Dell Premier Wireless Mouse', 'Premium wireless peripheral with multi-device flow technology', 59.99, 96),
('Logitech Lift Vertical Mouse', 'Ergonomic peripheral with vertical design for comfort', 69.99, 91),
('Razer Viper Ultimate Mouse', 'Wireless gaming peripheral with optical switches and dock', 149.99, 65),
('SteelSeries Prime Mouse', 'Esports gaming peripheral with pre-tensioned switches', 89.99, 84),

-- Periféricos - Teclados (30 productos)
('Logitech MX Keys Keyboard', 'Wireless keyboard with perfect stroke keys and backlighting', 99.99, 75),
('Razer BlackWidow V3 Keyboard', 'Gaming peripheral with Razer Green switches and RGB', 139.99, 63),
('Corsair K95 RGB Platinum Keyboard', 'Mechanical gaming keyboard with dedicated macro keys', 199.99, 52),
('SteelSeries Apex Pro Keyboard', 'Gaming peripheral with adjustable actuation and OLED display', 199.99, 50),
('Microsoft Surface Keyboard', 'Wireless keyboard with premium construction and Bluetooth', 129.99, 69),
('Apple Magic Keyboard', 'Wireless keyboard with numeric keypad and rechargeable battery', 129.99, 72),
('ASUS ROG Strix Scope Keyboard', 'Gaming peripheral with dedicated Ctrl key and USB passthrough', 149.99, 61),
('HP Wireless Keyboard', 'Basic wireless keyboard with spill-resistant design', 29.99, 118),
('Dell KB216 Wired Keyboard', 'Essential wired keyboard with multimedia keys', 19.99, 134),
('Logitech G915 TKL Keyboard', 'Wireless mechanical gaming keyboard with low-profile switches', 229.99, 46),
('Razer Huntsman Mini Keyboard', '60% gaming peripheral with optical switches and aluminum frame', 129.99, 70),
('Corsair K70 RGB MK.2 Keyboard', 'Mechanical gaming keyboard with per-key RGB and volume roller', 169.99, 58),
('SteelSeries Apex 7 Keyboard', 'Gaming peripheral with OLED smart display and aircraft aluminum', 159.99, 62),
('Microsoft Sculpt Ergonomic Keyboard', 'Wireless ergonomic peripheral with split keys and padded palm rest', 99.99, 81),
('Apple Magic Keyboard with Touch ID', 'Wireless keyboard with biometric security and numeric keypad', 179.99, 56),
('ASUS TUF Gaming K3 Keyboard', 'Military-grade gaming peripheral with IP56 water resistance', 79.99, 89),
('HP Omen Encoder Keyboard', 'Gaming peripheral with magnetic wrist rest and media controls', 119.99, 74),
('Dell Premier Wireless Keyboard', 'Premium wireless peripheral with multi-device pairing', 79.99, 88),
('Logitech K380 Multi-Device Keyboard', 'Compact wireless keyboard for multiple computers and tablets', 39.99, 109),
('Razer Ornata V2 Keyboard', 'Gaming peripheral with mecha-membrane keys and wrist rest', 99.99, 83),
('Corsair K65 RGB Mini Keyboard', '60% mechanical gaming keyboard with portable design', 99.99, 80),
('SteelSeries Apex 3 Keyboard', 'Gaming peripheral with IP32 water resistance and quiet switches', 59.99, 97),
('Microsoft Bluetooth Keyboard', 'Slim wireless peripheral with Bluetooth 5.0 and battery saving', 49.99, 103),
('Apple Wireless Keyboard', 'Slim aluminum keyboard with rechargeable battery', 79.99, 90),
('ASUS ROG Falchion Keyboard', '65% wireless gaming keyboard with interactive touch panel', 149.99, 67),
('HP Elite Folio Keyboard', 'Wireless keyboard with integrated stand and trackpoint', 149.99, 64),
('Dell KM7321W Wireless Keyboard', 'Wireless keyboard and mouse combo with productivity features', 89.99, 85),
('Logitech Ergo K860 Keyboard', 'Ergonomic split keyboard with curved design and wrist rest', 129.99, 73),
('Razer DeathStalker V2 Keyboard', 'Low-profile optical gaming keyboard with adjustable actuation', 149.99, 66),
('Corsair K100 RGB Keyboard', 'Mechanical gaming peripheral with opX optical switches and dial', 229.99, 49),

-- Periféricos - Auriculares (30 productos)
('SteelSeries Arctis Pro Headset', 'High-fidelity gaming headset with DTS Headphone:X 2.0', 179.99, 58),
('Logitech G Pro X Headset', 'Professional gaming headset with Blue VO!CE microphone', 129.99, 71),
('Razer BlackShark V2 Headset', 'Gaming headset with TriForce Titanium drivers and THX Spatial Audio', 99.99, 84),
('Corsair Virtuoso RGB Headset', 'Premium wireless gaming headset with high-fidelity audio', 199.99, 53),
('HyperX Cloud II Headset', 'Gaming headset with 53mm drivers and USB sound card', 99.99, 82),
('ASUS ROG Delta Headset', 'Gaming peripheral with Essence drivers and ASUS AI noise cancellation', 149.99, 66),
('Apple AirPods Max Headphones', 'Wireless over-ear headphones with Active Noise Cancellation', 549.99, 32),
('Bose QuietComfort 45 Headphones', 'Wireless noise cancelling headphones with up to 24-hour battery', 329.99, 47),
('Sony WH-1000XM5 Headphones', 'Wireless noise cancelling headphones with multiple microphones', 399.99, 41),
('Sennheiser HD 660 S Headphones', 'High-end open-back headphones for audiophiles and professionals', 499.99, 36),
('Audio-Technica ATH-M50x Headphones', 'Professional studio monitor headphones with exceptional clarity', 149.99, 68),
('Beyerdynamic DT 990 Pro Headphones', 'Open-back headphones for mixing and mastering applications', 179.99, 61),
('JBL Quantum 800 Headset', 'Wireless gaming headset with active noise cancellation and RGB', 199.99, 55),
('HP HyperX Cloud Alpha Headset', 'Dual chamber gaming headset with aluminum frame', 99.99, 80),
('Dell Professional Headset', 'Business headset with noise-cancelling microphone and comfortable ear cups', 79.99, 92),
('Logitech Zone Wireless Headset', 'Professional wireless headset for video conferencing and calls', 199.99, 54),
('Microsoft Surface Headphones 2', 'Wireless headphones with adjustable noise cancellation and dials', 249.99, 49),
('Plantronics Voyager Focus 2 Headset', 'Business headset with adaptive active noise cancellation', 229.99, 51),
('Jabra Evolve2 65 Headset', 'Professional wireless headset with busy light and call clarity', 279.99, 45),
('SteelSeries Arctis 9 Headset', 'Wireless gaming headset with simultaneous Bluetooth and 2.4GHz', 199.99, 57),
('Razer Kraken V3 Pro Headset', 'Gaming headset with HyperSense haptic feedback and RGB', 199.99, 56),
('Corsair HS80 RGB Headset', 'Wireless gaming headset with broadcast-grade microphone and Dolby Audio', 149.99, 69),
('HyperX Cloud Flight S Headset', 'Wireless gaming headset with Qi charging and 30-hour battery', 159.99, 65),
('ASUS ROG Theta 7.1 Headset', 'Gaming headset with quad ESS drivers and AI microphone noise cancellation', 299.99, 42),
('Apple AirPods Pro Earbuds', 'Wireless earbuds with active noise cancellation and transparency mode', 249.99, 73),
('Samsung Galaxy Buds2 Pro Earbuds', 'Wireless earbuds with 24-bit hi-fi sound and voice detect', 229.99, 67),
('Sony WF-1000XM4 Earbuds', 'Wireless noise cancelling earbuds with LDAC support', 279.99, 58),
('Bose QuietComfort Earbuds', 'Wireless earbuds with world-class noise cancellation and stay hear tips', 279.99, 59),
('Jabra Elite 85t Earbuds', 'Wireless earbuds with adjustable ANC and 6-microphone call technology', 229.99, 66),
('Beats Studio Buds Earbuds', 'Wireless earbuds with active noise cancellation and Apple H1 chip', 149.99, 78);
-- ==============================================
-- PC_COMPONENTS (30 productos)
-- ==============================================
INSERT INTO products_catalog (product_name, description, price, stock_quantity)
VALUES
    ('Intel Core i9-13900K CPU', 'PC Component: High-performance processor for gaming and content creation', 589.99, 20),
    ('AMD Ryzen 9 7950X CPU', 'PC Component: Powerful CPU for multitasking and professional workloads', 699.99, 15),
    ('NVIDIA RTX 4090 GPU', 'PC Component: Top-tier graphics card for gaming and 3D rendering', 1599.99, 10),
    ('Corsair Vengeance 32GB DDR5 RAM', 'PC Component: High-speed memory kit for desktops', 149.99, 50),
    ('Samsung 970 EVO Plus 2TB NVMe SSD', 'PC Component: Fast internal storage for high-performance PCs', 249.99, 30),
    ('Intel Core i7-13700K CPU', 'PC Component: Balanced CPU for gaming and productivity', 419.99, 25),
    ('AMD Ryzen 7 7800X3D CPU', 'PC Component: High-efficiency CPU for gaming performance', 449.99, 18),
    ('NVIDIA RTX 4080 GPU', 'PC Component: High-end graphics card for 4K gaming', 1199.99, 12),
    ('G.SKILL Trident Z 16GB DDR4 RAM', 'PC Component: Reliable memory for gaming PCs', 79.99, 60),
    ('Kingston Fury Beast 32GB DDR5 RAM', 'PC Component: Fast memory for modern desktops', 159.99, 40),
    ('Western Digital Black SN850 1TB NVMe SSD', 'PC Component: High-speed SSD for gaming and storage', 129.99, 45),
    ('Seagate FireCuda 530 2TB NVMe SSD', 'PC Component: High-end gaming SSD with large capacity', 279.99, 25),
    ('ASRock B650 Motherboard', 'PC Component: AM5 motherboard for AMD Ryzen CPUs', 199.99, 30),
    ('MSI Z790 Gaming Motherboard', 'PC Component: Intel motherboard with gaming features', 349.99, 20),
    ('Cooler Master Hyper 212 CPU Cooler', 'PC Component: Efficient cooling for desktop CPUs', 39.99, 55),
    ('Noctua NH-D15 CPU Cooler', 'PC Component: Premium cooling for high-performance CPUs', 99.99, 18),
    ('Corsair RM850x Power Supply', 'PC Component: Reliable PSU for gaming and professional PCs', 139.99, 30),
    ('EVGA SuperNOVA 1000W PSU', 'PC Component: High-capacity power supply for workstations', 249.99, 12),
    ('NZXT H510 PC Case', 'PC Component: Sleek case for mid-tower builds', 79.99, 40),
    ('Fractal Design Meshify 2 Case', 'PC Component: High airflow PC case for enthusiasts', 159.99, 22),
    ('ASUS ROG Strix GPU Backplate', 'PC Component: Custom backplate for RTX graphics cards', 49.99, 60),
    ('EKWB Water Cooling Kit', 'PC Component: Complete liquid cooling solution', 299.99, 15),
    ('Intel Optane Memory 32GB', 'PC Component: Cache memory to accelerate storage', 69.99, 40),
    ('AMD Wraith Prism Cooler', 'PC Component: CPU cooler with RGB lighting', 49.99, 35),
    ('Gigabyte GeForce RTX 4070 GPU', 'PC Component: High-performance graphics card for gaming', 599.99, 28),
    ('ASUS TUF Gaming GPU', 'PC Component: Durable and efficient graphics card', 699.99, 20),
    ('Crucial P5 Plus 1TB NVMe SSD', 'PC Component: Fast storage for desktops and laptops', 109.99, 50),
    ('Patriot Viper Steel 16GB DDR5 RAM', 'PC Component: High-speed memory for gaming systems', 89.99, 45),
    ('Thermaltake Toughpower 850W PSU', 'PC Component: Reliable power supply for high-end builds', 159.99, 22),
    ('MSI MPG Z790 Carbon Motherboard', 'PC Component: Gaming motherboard with premium features', 329.99, 18);


-- ==============================================
-- STORAGE_DEVICES (29 productos)
-- ==============================================
INSERT INTO products_catalog (product_name, description, price, stock_quantity)
VALUES
    ('Seagate IronWolf 4TB NAS HDD', 'Storage Device: Reliable hard drive for NAS and desktop storage', 99.99, 45),
    ('Western Digital Blue 1TB SSD', 'Storage Device: SATA SSD for everyday computing and storage upgrades', 79.99, 60),
    ('Samsung T7 2TB Portable SSD', 'Storage Device: High-speed external SSD for backups and mobility', 199.99, 35),
    ('SanDisk Extreme Pro 1TB SD Card', 'Storage Device: High-performance SD card for cameras and storage expansion', 149.99, 40),
    ('LaCie Rugged 5TB External HDD', 'Storage Device: Durable external hard drive for portable backups', 179.99, 25),
    ('Western Digital Black 2TB SSD', 'Storage Device: High-speed internal storage for gaming PCs', 219.99, 28),
    ('Seagate Barracuda 2TB HDD', 'Storage Device: Affordable desktop storage solution', 59.99, 50),
    ('Samsung 980 Pro 1TB NVMe SSD', 'Storage Device: Fast NVMe SSD for high-performance PCs', 149.99, 38),
    ('Kingston KC3000 2TB NVMe SSD', 'Storage Device: High-capacity and high-speed internal storage', 259.99, 20),
    ('Crucial MX500 1TB SSD', 'Storage Device: Reliable SATA SSD for desktops and laptops', 99.99, 45),
    ('ADATA XPG SX8200 Pro 1TB NVMe SSD', 'Storage Device: High-performance SSD for gaming systems', 129.99, 35),
    ('Western Digital My Passport 4TB External HDD', 'Storage Device: Portable external drive for backups', 119.99, 30),
    ('SanDisk Extreme Portable SSD 2TB', 'Storage Device: Compact high-speed storage for on-the-go use', 249.99, 22),
    ('Seagate FireCuda Gaming SSD 1TB', 'Storage Device: External SSD optimized for gaming', 189.99, 18),
    ('LaCie d2 Professional 8TB HDD', 'Storage Device: High-capacity desktop storage solution', 399.99, 12),
    ('Samsung T5 1TB Portable SSD', 'Storage Device: Compact external SSD for daily usage', 129.99, 40),
    ('WD Elements 2TB HDD', 'Storage Device: Affordable external storage solution', 89.99, 45),
    ('Seagate Backup Plus 5TB HDD', 'Storage Device: High-capacity external backup drive', 159.99, 28),
    ('Crucial P3 1TB NVMe SSD', 'Storage Device: Budget-friendly NVMe storage for PCs', 89.99, 50),
    ('ADATA SE800 1TB Portable SSD', 'Storage Device: External high-speed SSD with USB-C', 149.99, 25),
    ('Samsung 870 QVO 2TB SSD', 'Storage Device: High-capacity SATA SSD for desktops', 149.99, 32),
    ('Kingston Canvas Go! 512GB SD Card', 'Storage Device: Durable SD card for cameras', 39.99, 60),
    ('SanDisk Ultra 2TB SD Card', 'Storage Device: High-capacity SD card for media storage', 79.99, 45),
    ('Seagate Expansion 4TB HDD', 'Storage Device: Easy-to-use external storage', 99.99, 40),
    ('Western Digital Red 6TB HDD', 'Storage Device: NAS-optimized storage for small servers', 179.99, 20),
    ('LaCie Rugged SSD Pro 1TB', 'Storage Device: Durable high-speed external SSD', 299.99, 15),
    ('Samsung 980 1TB NVMe SSD', 'Storage Device: Fast internal storage solution', 119.99, 35),
    ('Seagate IronWolf Pro 10TB HDD', 'Storage Device: High-capacity NAS storage', 399.99, 10),
    ('Western Digital Ultrastar DC HC330 8TB', 'Storage Device: Enterprise-grade HDD for heavy workloads', 349.99, 8);

-- ==============================================
-- NETWORKING_EQUIPMENT (30 productos)
-- ==============================================
INSERT INTO products_catalog (product_name, description, price, stock_quantity)
VALUES
    ('TP-Link Archer AX6000 Router', 'Networking Equipment: High-speed Wi-Fi 6 router for home and office', 299.99, 30),
    ('Netgear Nighthawk AX12 Router', 'Networking Equipment: Premium router with advanced network management features', 399.99, 20),
    ('Ubiquiti UniFi Switch 24', 'Networking Equipment: Managed switch for professional networking setups', 499.99, 15),
    ('Asus RT-AX86U Gaming Router', 'Networking Equipment: Gaming router with low latency and QoS features', 249.99, 25),
    ('Cisco SG350 28-Port Switch', 'Networking Equipment: Enterprise-grade switch for small to medium businesses', 599.99, 10),
    ('TP-Link TL-SG108 Switch', 'Networking Equipment: Simple unmanaged 8-port gigabit switch', 39.99, 50),
    ('Netgear GS308 Switch', 'Networking Equipment: Compact gigabit switch for home networks', 49.99, 45),
    ('Ubiquiti UniFi AP AC Lite', 'Networking Equipment: Access point for stable Wi-Fi coverage', 89.99, 30),
    ('Asus RT-AX58U Router', 'Networking Equipment: Dual-band Wi-Fi 6 router with stable connectivity', 179.99, 35),
    ('Cisco RV340 Router', 'Networking Equipment: Business-class router with VPN support', 299.99, 18),
    ('TP-Link Archer AX73 Router', 'Networking Equipment: High-speed Wi-Fi 6 router for medium homes', 199.99, 28),
    ('Netgear Orbi RBK752 Mesh', 'Networking Equipment: Whole-home Wi-Fi 6 mesh system', 349.99, 20),
    ('Ubiquiti UniFi Dream Machine', 'Networking Equipment: All-in-one router, switch, and access point', 399.99, 12),
    ('Asus ROG Rapture GT-AX11000 Router', 'Networking Equipment: Tri-band gaming router with high throughput', 499.99, 15),
    ('Cisco Catalyst 2960 Switch', 'Networking Equipment: Enterprise network switch with advanced features', 899.99, 8),
    ('TP-Link TL-WR940N Router', 'Networking Equipment: Budget Wi-Fi router for home use', 29.99, 60),
    ('Netgear Nighthawk R7000 Router', 'Networking Equipment: Dual-band Wi-Fi router for gaming', 159.99, 35),
    ('Ubiquiti UniFi Switch 16', 'Networking Equipment: Managed 16-port switch for small setups', 299.99, 15),
    ('Asus RT-AX82U Router', 'Networking Equipment: Gaming Wi-Fi 6 router with QoS', 199.99, 25),
    ('Cisco Meraki MX64 Router', 'Networking Equipment: Cloud-managed security appliance', 399.99, 10),
    ('TP-Link Deco X60 Mesh', 'Networking Equipment: Whole-home Wi-Fi 6 mesh system', 249.99, 22),
    ('Netgear Nighthawk EAX80 Mesh', 'Networking Equipment: High-speed Wi-Fi 6E mesh system', 349.99, 18),
    ('Ubiquiti EdgeRouter X', 'Networking Equipment: Small business router with gigabit ports', 129.99, 30),
    ('Asus RT-AX55 Router', 'Networking Equipment: Affordable Wi-Fi 6 router', 119.99, 40),
    ('Cisco RV160 Router', 'Networking Equipment: Small business VPN router', 179.99, 20),
    ('TP-Link TL-SG105 Switch', 'Networking Equipment: Compact 5-port gigabit switch', 24.99, 60),
    ('Netgear GS105 Switch', 'Networking Equipment: Reliable small desktop switch', 29.99, 55),
    ('Ubiquiti UniFi AP NanoHD', 'Networking Equipment: High-density access point for enterprise', 129.99, 15),
    ('Asus RT-AX68U Router', 'Networking Equipment: Wi-Fi 6 router with stable connectivity', 199.99, 25),
    ('Cisco SG250 Switch', 'Networking Equipment: Managed 24-port switch for medium businesses', 349.99, 12);

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'MONITORS'
WHERE p.product_name ILIKE '%monitor%'
   OR p.description ILIKE '%monitor%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'HOME_COMPUTERS'
WHERE p.product_name ILIKE '%home computer%'
   OR p.product_name ILIKE '%home desktop%'
   OR p.description ILIKE '%home computer%'
   OR p.description ILIKE '%home desktop%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'PROFESSIONAL_WORKSTATIONS'
WHERE p.product_name ILIKE '%professional computer%'
   OR p.product_name ILIKE '%professional desktop%'
   OR p.product_name ILIKE '%workstation%'
   OR p.description ILIKE '%professional computer%'
   OR p.description ILIKE '%professional desktop%'
   OR p.description ILIKE '%workstation%'
   OR p.description ILIKE 'professional%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'PC_COMPONENTS'
WHERE p.description ILIKE '%PC Component%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'STORAGE_DEVICES'
WHERE p.description ILIKE '%Storage Device%'
   OR p.product_name ILIKE '%SSD%'
   OR p.product_name ILIKE '%HDD%'
   OR p.product_name ILIKE '%NVMe%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'NETWORKING_EQUIPMENT'
WHERE p.description ILIKE '%Networking equipment%'
   OR p.product_name ILIKE '%router%'
   OR p.product_name ILIKE '%switch%'
   OR p.product_name ILIKE '%access point%'
   OR p.product_name ILIKE '%mesh%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'MICE'
WHERE p.product_name ILIKE '%mouse%'
   OR p.description ILIKE '%mouse%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'KEYBOARDS'
WHERE p.product_name ILIKE '%keyboard%'
   OR p.description ILIKE '%keyboard%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'GAMING_COMPUTERS'
WHERE p.product_name ILIKE '%gaming%'
   OR p.description ILIKE '%gaming%';

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products_catalog p
         JOIN categories c ON c.category = 'HEADSETS'
WHERE p.product_name ILIKE '%headset%'
   OR p.product_name ILIKE '%headphones%'
   OR p.product_name ILIKE '%earbuds%'
   OR p.description ILIKE '%headset%'
   OR p.description ILIKE '%headphones%';
