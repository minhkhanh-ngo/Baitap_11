IF DB_ID('QuanLyVideo_De04') IS NULL
    CREATE DATABASE QuanLyVideo_De04;
GO

USE QuanLyVideo_De04;
GO

IF OBJECT_ID('Category', 'U') IS NULL
CREATE TABLE Category (
                          CategoryId   INT IDENTITY(1,1) PRIMARY KEY,
                          Categoryname NVARCHAR(100),
                          Categorycode NVARCHAR(100),
                          Images       NVARCHAR(500),
                          Status       BIT DEFAULT 1
);
GO

IF OBJECT_ID('Users', 'U') IS NULL
CREATE TABLE Users (
                       Username NVARCHAR(50) PRIMARY KEY,
                       Password NVARCHAR(50),
                       Phone    NVARCHAR(15),
                       Fullname NVARCHAR(50),
                       Email    NVARCHAR(150),
                       Admin    BIT DEFAULT 0,
                       Active   BIT DEFAULT 1,
                       Images   NVARCHAR(500)
);
GO

IF OBJECT_ID('Videos', 'U') IS NULL
CREATE TABLE Videos (
                        VideoId     NVARCHAR(50) PRIMARY KEY,
                        Title       NVARCHAR(200),
                        Poster      NVARCHAR(500),
                        Views       INT DEFAULT 0,
                        Description NVARCHAR(500),
                        Active      BIT DEFAULT 1,
                        CategoryId  INT FOREIGN KEY REFERENCES Category(CategoryId),
                        Price       DECIMAL(18,0) NOT NULL DEFAULT 0,
                        Stock       INT NOT NULL DEFAULT 0
);
GO

IF OBJECT_ID('Shares', 'U') IS NULL
CREATE TABLE Shares (
                        ShareId    INT IDENTITY(1,1) PRIMARY KEY,
                        Emails     NVARCHAR(50),
                        SharedDate DATE,
                        Username   NVARCHAR(50) FOREIGN KEY REFERENCES Users(Username),
                        VideoId    NVARCHAR(50) FOREIGN KEY REFERENCES Videos(VideoId)
);
GO

IF OBJECT_ID('Favorites', 'U') IS NULL
CREATE TABLE Favorites (
                           FavoriteId INT IDENTITY(1,1) PRIMARY KEY,
                           LikedDate  DATE,
                           VideoId    NVARCHAR(50) FOREIGN KEY REFERENCES Videos(VideoId),
                           Username   NVARCHAR(50) FOREIGN KEY REFERENCES Users(Username)
);
GO

IF COL_LENGTH('Videos', 'Price') IS NULL
ALTER TABLE Videos ADD Price DECIMAL(18,0) NOT NULL CONSTRAINT DF_Videos_Price DEFAULT 0;
GO
IF COL_LENGTH('Videos', 'Stock') IS NULL
ALTER TABLE Videos ADD Stock INT NOT NULL CONSTRAINT DF_Videos_Stock DEFAULT 0;
GO

IF OBJECT_ID('Orders', 'U') IS NULL
CREATE TABLE Orders (
                        OrderId       INT IDENTITY(1,1) PRIMARY KEY,
                        Username      NVARCHAR(50)  NOT NULL FOREIGN KEY REFERENCES Users(Username),
                        ReceiverName  NVARCHAR(100) NOT NULL,
                        Phone         NVARCHAR(15)  NOT NULL,
                        Address       NVARCHAR(300) NOT NULL,
                        Note          NVARCHAR(500) NULL,
                        TotalAmount   DECIMAL(18,0) NOT NULL,
                        PaymentMethod NVARCHAR(20)  NOT NULL DEFAULT 'COD',
                        Status        NVARCHAR(30)  NOT NULL DEFAULT N'Chờ xác nhận',
                        CreatedDate   DATETIME      NOT NULL DEFAULT GETDATE()
);
GO

IF OBJECT_ID('OrderDetails', 'U') IS NULL
CREATE TABLE OrderDetails (
                              OrderDetailId INT IDENTITY(1,1) PRIMARY KEY,
                              OrderId       INT           NOT NULL FOREIGN KEY REFERENCES Orders(OrderId),
                              VideoId       NVARCHAR(50)  NOT NULL FOREIGN KEY REFERENCES Videos(VideoId),
                              Quantity      INT           NOT NULL CHECK (Quantity > 0),
                              UnitPrice     DECIMAL(18,0) NOT NULL
);
GO

INSERT INTO Category (Categoryname, Categorycode, Status)
SELECT v.Categoryname, v.Categorycode, 1
FROM (VALUES
          (N'Hành động',          'HD'),
          (N'Hoạt hình',          'HH'),
          (N'Khoa học viễn tưởng','KVT'),
          (N'Tình cảm',           'TC')
     ) AS v(Categoryname, Categorycode)
WHERE NOT EXISTS (SELECT 1 FROM Category c WHERE c.Categorycode = v.Categorycode);
GO

INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active)
SELECT v.Username, v.Password, v.Phone, v.Fullname, v.Email, v.Admin, 1
FROM (VALUES
          ('admin', '123', '0376224417', N'Ngô Minh Khánh', 'admin@gmail.com',  1),
          ('user01', '123', '0900000001', N'Nguyễn Văn A', 'user1@gmail.com',  0),
          ('user02', '123', '0900000002', N'Trần Thị B',   'user2@gmail.com',  0),
          ('user03', '123', '0900000003', N'Lê Văn C',     'user3@gmail.com',  0),
          ('user04', '123', '0900000004', N'Phạm Thị D',   'user4@gmail.com',  0),
          ('user05', '123', '0900000005', N'Hoàng Văn E',  'user5@gmail.com',  0),
          ('user06', '123', '0900000006', N'Đặng Thị F',   'user6@gmail.com',  0),
          ('user07', '123', '0900000007', N'Vũ Văn G',     'user7@gmail.com',  0),
          ('user08', '123', '0900000008', N'Võ Thị H',     'user8@gmail.com',  0),
          ('user09', '123', '0900000009', N'Phan Văn I',   'user9@gmail.com',  0),
          ('user10', '123', '0900000010', N'Đinh Thị K',   'user10@gmail.com', 0),
          ('user11', '123', '0900000011', N'Lý Văn L',     'user11@gmail.com', 0),
          ('user12', '123', '0900000012', N'Bùi Thị M',    'user12@gmail.com', 0),
          ('user13', '123', '0900000013', N'Đỗ Văn N',     'user13@gmail.com', 0),
          ('user14', '123', '0900000014', N'Hồ Thị O',     'user14@gmail.com', 0),
          ('user15', '123', '0900000015', N'Ngô Văn P',    'user15@gmail.com', 0)
     ) AS v(Username, Password, Phone, Fullname, Email, Admin)
WHERE NOT EXISTS (SELECT 1 FROM Users u WHERE u.Username = v.Username);
GO

INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId, Price, Stock)
SELECT v.VideoId, v.Title, 'https://via.placeholder.com/220x160', v.Views, v.Description, 1,
       (SELECT TOP 1 c.CategoryId FROM Category c WHERE c.Categorycode = v.CatCode),
       v.Price, v.Stock
FROM (VALUES
          ('V001', N'Phim Siêu Nhân Chiến Đấu', 150, N'Phim hành động siêu nhân cực hay',        'HD',  150000, 20),
          ('V002', N'Đại Chiến Vũ Trụ',         320, N'Phim khoa học viễn tưởng hoành tráng',   'KVT', 250000, 15),
          ('V003', N'Chú Mèo Thông Minh',       500, N'Hoạt hình vui nhộn cho mọi lứa tuổi',    'HH',   99000, 30),
          ('V004', N'Bản Tình Ca Mùa Đông',      89, N'Phim tình cảm lãng mạn',                 'TC',  120000,  5)
     ) AS v(VideoId, Title, Views, Description, CatCode, Price, Stock)
WHERE NOT EXISTS (SELECT 1 FROM Videos x WHERE x.VideoId = v.VideoId);
GO

UPDATE Videos SET Price = 150000, Stock = 20 WHERE VideoId = 'V001' AND Price = 0;
UPDATE Videos SET Price = 250000, Stock = 15 WHERE VideoId = 'V002' AND Price = 0;
UPDATE Videos SET Price =  99000, Stock = 30 WHERE VideoId = 'V003' AND Price = 0;
UPDATE Videos SET Price = 120000, Stock =  5 WHERE VideoId = 'V004' AND Price = 0;
GO

UPDATE Videos SET Poster = '/images/' + LOWER(VideoId) + '.svg' WHERE VideoId IN ('V001','V002','V003','V004');
GO