USE QuanLyVideo_De04;
GO

INSERT INTO Orders (Username, ReceiverName, Phone, Address, Note, TotalAmount, PaymentMethod, Status, CreatedDate)
VALUES
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: mới',        150000, 'COD', N'Đơn hàng mới',  DATEADD(MINUTE, -80, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: xác nhận',   250000, 'COD', N'Đã xác nhận',   DATEADD(MINUTE, -70, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: chuẩn bị',    99000, 'COD', N'Chuẩn bị hàng', DATEADD(MINUTE, -60, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: vận chuyển', 120000, 'COD', N'Vận chuyển',    DATEADD(MINUTE, -50, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: giao hàng',  300000, 'COD', N'Giao hàng',     DATEADD(MINUTE, -40, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: đã giao',    150000, 'COD', N'Đã giao',       DATEADD(MINUTE, -30, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: hủy',        250000, 'COD', N'Đơn hàng hủy',  DATEADD(MINUTE, -20, GETDATE())),
 ('user01', N'Nguyễn Văn A', '0900000001', N'12 Võ Văn Ngân, Thủ Đức, TP.HCM', N'Test: hoàn',        99000, 'COD', N'Đơn hàng hoàn', DATEADD(MINUTE, -10, GETDATE()));
GO

INSERT INTO OrderDetails (OrderId, VideoId, Quantity, UnitPrice)
SELECT o.OrderId, x.VideoId, x.Quantity, x.UnitPrice
FROM Orders o
JOIN (VALUES
    (N'Test: mới',        'V001', 1, 150000),
    (N'Test: xác nhận',   'V002', 1, 250000),
    (N'Test: chuẩn bị',   'V003', 1,  99000),
    (N'Test: vận chuyển', 'V004', 1, 120000),
    (N'Test: giao hàng',  'V001', 2, 150000),
    (N'Test: đã giao',    'V001', 1, 150000),
    (N'Test: hủy',        'V002', 1, 250000),
    (N'Test: hoàn',       'V003', 1,  99000)
) AS x(Note, VideoId, Quantity, UnitPrice) ON o.Note = x.Note
WHERE o.Username = 'user01';
GO

GO
