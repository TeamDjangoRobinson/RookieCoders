

import UIKit

class ViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    var redFilteredImage: UIImage?
    var greenFilteredImage: UIImage?
    var blueFilteredImage: UIImage?
    var yellowFilteredImage: UIImage?
    var purpleFilteredImage: UIImage?
    var filteredImage: UIImage?
    var oriImage: UIImage?
    var intenFlag = 0
    
    @IBOutlet weak var redFilter: UIButton!
    @IBAction func onRedPress(sender: UIButton) {
        let rgbaImage = RGBAImage(image: oriImage!)!
        let avegRed = 126
        for y in 0..<rgbaImage.height {
            for x in 0..<rgbaImage.width {
                let index = y * rgbaImage.width + x
                var pixel = rgbaImage.pixels[index]
                let redDelta = Int(pixel.red) - avegRed
                
                var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                if (Int(pixel.red) < avegRed) {
                    modifier = 1
                    
                }
                pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                rgbaImage.pixels[index] = pixel
            }
        }
        self.redFilteredImage = rgbaImage.toUIImage()!
        imageView.image = redFilteredImage
        filteredImage = redFilteredImage
        intenFlag = 1
        compButton.enabled = true
        compButton.tag = 1
    }
    @IBOutlet weak var greenFilter: UIButton!
    @IBAction func onGreenPress(sender: UIButton) {
        //let avgBlue = 50
        let rgbaImage = RGBAImage(image: oriImage!)!
        let avgGreen = 50
        for y in 0..<rgbaImage.height {
            for x in 0..<rgbaImage.width {
                let index = y * rgbaImage.width + x
                var pixel = rgbaImage.pixels[index]
                let greenDelta = Int(pixel.green) - avgGreen
                
                var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                if (Int(pixel.green) < avgGreen) {
                    modifier = 1
                    
                }
                pixel.green = UInt8(max(min(255,Int(round(Double(avgGreen) + 0.5 * modifier * Double(greenDelta)))), 0))
                rgbaImage.pixels[index] = pixel
            }
        }
        self.greenFilteredImage = rgbaImage.toUIImage()!
        
        imageView.image = greenFilteredImage
        filteredImage = greenFilteredImage
        intenFlag = 2
        compButton.enabled = true
        compButton.tag = 1
    }
    @IBOutlet weak var blueFilter: UIButton!
    @IBAction func onBluePress(sender: UIButton) {
        let rgbaImage = RGBAImage(image: oriImage!)!
        let avgBlue = 50
        for y in 0..<rgbaImage.height {
            for x in 0..<rgbaImage.width {
                let index = y * rgbaImage.width + x
                var pixel = rgbaImage.pixels[index]
                let blueDelta = Int(pixel.blue) - avgBlue
                
                var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                if (Int(pixel.blue) < avgBlue) {
                    modifier = 1
                    
                }
                pixel.blue = UInt8(max(min(255,Int(round(Double(avgBlue) + modifier * Double(blueDelta)))), 0))
                rgbaImage.pixels[index] = pixel
            }
        }
        self.blueFilteredImage = rgbaImage.toUIImage()!
        imageView.image = blueFilteredImage
        filteredImage = blueFilteredImage
        intenFlag = 3
        compButton.enabled = true
        compButton.tag = 1
    }
    @IBOutlet weak var yellowFilter: UIButton!
    @IBAction func onYellowPress(sender: UIButton) {
        let avegRed = 50
        let avgGreen = 50
        
        let rgbaImage = RGBAImage(image: oriImage!)!
        for y in 0..<rgbaImage.height {
            for x in 0..<rgbaImage.width {
                let index = y * rgbaImage.width + x
                var pixel = rgbaImage.pixels[index]
                let redDelta = Int(pixel.red) - avegRed
                let greenDelta = Int(pixel.green) -  avgGreen
                
                var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                if (Int(pixel.red) < avgGreen) {
                    modifier = 1
                    
                }
                pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                pixel.green = UInt8(max(min(255,Int(round(Double(avgGreen) + modifier * Double(greenDelta)))), 0))
                
                rgbaImage.pixels[index] = pixel
            }
        }
        self.yellowFilteredImage = rgbaImage.toUIImage()!
        
        
        imageView.image = yellowFilteredImage
        filteredImage = yellowFilteredImage
        intenFlag = 4
        compButton.enabled = true
        compButton.tag = 1
    }
    @IBOutlet weak var purpleFilter: UIButton!
    @IBAction func onPurplePress(sender: UIButton) {
        let avegRed = 50
        let avgBlue = 50
        let rgbaImage = RGBAImage(image: oriImage!)!
        for y in 0..<rgbaImage.height {
            for x in 0..<rgbaImage.width {
                let index = y * rgbaImage.width + x
                var pixel = rgbaImage.pixels[index]
                let blueDelta = Int(pixel.blue) - avgBlue
                let redDelta = Int(pixel.red) -  avegRed
                
                var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                if (Int(pixel.blue) < avgBlue) {
                    modifier = 1
                    
                }
                pixel.blue = UInt8(max(min(255,Int(round(Double(avgBlue) + modifier * Double(blueDelta)))), 0))
                pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                
                rgbaImage.pixels[index] = pixel
            }
        }
        self.purpleFilteredImage = rgbaImage.toUIImage()!
        
        imageView.image = purpleFilteredImage
        filteredImage = purpleFilteredImage
        intenFlag = 5
        compButton.enabled = true
        compButton.tag = 1
    }
    
    @IBOutlet weak var compButton: UIButton!
    @IBAction func onCompPress(sender: UIButton) {
        if compButton.selected || compButton.tag == 1{
            imageView.image = oriImage
            compButton.selected = false
            compButton.tag = 0
            oriLabel.hidden = false
            
        }else{
            imageView.image = filteredImage
            compButton.selected = true
            oriLabel.hidden = true
            
        }
    }
    
    @IBOutlet var sliderView: UIView!
    @IBOutlet weak var sliderVal: UISlider!
    @IBAction func onSliderTap(sender: UISlider) {
        sliderVal.maximumValue = 200
        sliderVal.minimumValue = -100
        sliderVal.continuous = true
        //sliderVal.value = 50
        
        switch intenFlag {
        case 1:
            let rgbaImage = RGBAImage(image: filteredImage!)!
            let avegRed = sliderVal.value
            for y in 0..<rgbaImage.height {
                for x in 0..<rgbaImage.width {
                    let index = y * rgbaImage.width + x
                    var pixel = rgbaImage.pixels[index]
                    let redDelta = Int(pixel.red) + Int(avegRed)
                    
                    var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                    if (Int(pixel.red) < Int(avegRed)) {
                        modifier = 1
                        
                    }
                    pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                    rgbaImage.pixels[index] = pixel
                }
            }
            self.redFilteredImage = rgbaImage.toUIImage()!
            imageView.image = redFilteredImage
            
        case 3:
            let rgbaImage = RGBAImage(image: filteredImage!)!
            let avgBlue = sliderVal.value
            for y in 0..<rgbaImage.height {
                for x in 0..<rgbaImage.width {
                    let index = y * rgbaImage.width + x
                    var pixel = rgbaImage.pixels[index]
                    let blueDelta = Int(pixel.blue) + Int(avgBlue)
                    
                    var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                    if (Int(pixel.blue) < Int(avgBlue)) {
                        modifier = 1
                        
                    }
                    pixel.blue = UInt8(max(min(255,Int(round(Double(avgBlue) + modifier * Double(blueDelta)))), 0))
                    rgbaImage.pixels[index] = pixel
                }
            }
            self.blueFilteredImage = rgbaImage.toUIImage()!
            imageView.image = blueFilteredImage
            
        case 2:
            let rgbaImage = RGBAImage(image: filteredImage!)!
            let avgGreen = sliderVal.value
            for y in 0..<rgbaImage.height {
                for x in 0..<rgbaImage.width {
                    let index = y * rgbaImage.width + x
                    var pixel = rgbaImage.pixels[index]
                    let greenDelta = Int(pixel.green) + Int(avgGreen)
                    
                    var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                    if (Int(pixel.green) < Int(avgGreen)) {
                        modifier = 1
                        
                    }
                    pixel.green = UInt8(max(min(255,Int(round(Double(avgGreen) + 0.5 * modifier * Double(greenDelta)))), 0))
                    rgbaImage.pixels[index] = pixel
                }
            }
            self.greenFilteredImage = rgbaImage.toUIImage()!
            imageView.image = greenFilteredImage
            
        case 4:
            let avegRed = Int(sliderVal.value)
            let avgGreen = Int(sliderVal.value)
            
            let rgbaImage = RGBAImage(image: oriImage!)!
            for y in 0..<rgbaImage.height {
                for x in 0..<rgbaImage.width {
                    let index = y * rgbaImage.width + x
                    var pixel = rgbaImage.pixels[index]
                    let redDelta = Int(pixel.red) + avegRed
                    let greenDelta = Int(pixel.green) +  avgGreen
                    
                    var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                    if (Int(pixel.red) < avgGreen) {
                        modifier = 1
                        
                    }
                    pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                    pixel.green = UInt8(max(min(255,Int(round(Double(avgGreen) + modifier * Double(greenDelta)))), 0))
                    
                    rgbaImage.pixels[index] = pixel
                }
            }
            self.yellowFilteredImage = rgbaImage.toUIImage()!
            imageView.image = yellowFilteredImage

        case 5:
            let avegRed = Int(sliderVal.value)
            let avgBlue = Int(sliderVal.value)
            let rgbaImage = RGBAImage(image: oriImage!)!
            for y in 0..<rgbaImage.height {
                for x in 0..<rgbaImage.width {
                    let index = y * rgbaImage.width + x
                    var pixel = rgbaImage.pixels[index]
                    let blueDelta = Int(pixel.blue) + avgBlue
                    let redDelta = Int(pixel.red) +  avegRed
                    
                    var modifier = 1 + 4 * (Double(y) / Double(rgbaImage.height))
                    if (Int(pixel.blue) < avgBlue) {
                        modifier = 1
                        
                    }
                    pixel.blue = UInt8(max(min(255,Int(round(Double(avgBlue) + modifier * Double(blueDelta)))), 0))
                    pixel.red = UInt8(max(min(255,Int(round(Double(avegRed) + modifier * Double(redDelta)))), 0))
                    
                    rgbaImage.pixels[index] = pixel
                }
            }
            self.purpleFilteredImage = rgbaImage.toUIImage()!
            
            imageView.image = purpleFilteredImage
            
            
        default: break

        }
        
    }
    
    
    
    @IBOutlet weak var editButton: UIButton!
    
    func showSliderView() {
        view.addSubview(sliderView)
        
        sliderView.translatesAutoresizingMaskIntoConstraints = false
        
        let bottomConstraint = sliderView.bottomAnchor.constraintEqualToAnchor(bottomMenu.topAnchor)
        let leftConstraint = sliderView.leftAnchor.constraintEqualToAnchor(view.leftAnchor)
        let rightConstraint = sliderView.rightAnchor.constraintEqualToAnchor(view.rightAnchor)
        
        let heightConstraint = sliderView.heightAnchor.constraintEqualToConstant(44)
        
        NSLayoutConstraint.activateConstraints([bottomConstraint, leftConstraint, rightConstraint, heightConstraint])
        
        view.layoutIfNeeded()
        
        self.sliderView.alpha = 0
        UIView.animateWithDuration(0.4) {
            self.sliderView.alpha = 1.0
        }
    }
    
    func hideSliderView() {
        UIView.animateWithDuration(0.4, animations: {
            self.sliderView.alpha = 0
        }) { completed in
            if completed == true {
                self.sliderView.removeFromSuperview()
            }
        }
    }
    
    @IBAction func onEditPress(sender: UIButton) {
        sliderView.hidden = false
        if (sender.selected) {
            hideSliderView()
            sender.selected = false
        } else {
            showSliderView()
            sender.selected = true
        }
    }
    
    
    @IBOutlet var LongPress: UILongPressGestureRecognizer!
    @IBAction func onLongPress(sender: UILongPressGestureRecognizer) {
        LongPress.minimumPressDuration = 1
        
        if (LongPress.state == UIGestureRecognizerState.Began) {
            imageView.image = oriImage
            oriLabel.hidden = false
        } else if (LongPress.state == UIGestureRecognizerState.Ended){
            imageView.image = filteredImage
            oriLabel.hidden = true
        }
    }
    
    
    @IBOutlet weak var oriLabel: UILabel!
    @IBOutlet var imageView: UIImageView!
    
    @IBOutlet var secondaryMenu: UIView!
    @IBOutlet var bottomMenu: UIView!
    
    @IBOutlet var filterButton: UIButton!
    
    override func viewDidLoad() {
        //redFilter.setTitle("Show Before Image", forState: .Selected)
        super.viewDidLoad()
        secondaryMenu.backgroundColor = UIColor.whiteColor().colorWithAlphaComponent(0.5)
        secondaryMenu.translatesAutoresizingMaskIntoConstraints = false
        oriImage=UIImage(named: "scenery.jpg")!
        LongPress.enabled = true
        self.view.addGestureRecognizer(LongPress)
        //let image = UIImage(named: "test.jpg")!
        //let image = oriImage
        //let rgbaImage = RGBAImage(image: oriImage!)!
        
        oriLabel.hidden = true
        compButton.enabled = false
        compButton.tag = 1
        //sliderView.hidden = true
        
        
        
        
    }

    // MARK: Share
    @IBAction func onShare(sender: AnyObject) {
        let activityController = UIActivityViewController(activityItems: ["Check out our really cool app", imageView.image!], applicationActivities: nil)
        presentViewController(activityController, animated: true, completion: nil)
    }
    
    // MARK: New Photo
    @IBAction func onNewPhoto(sender: AnyObject) {
        let actionSheet = UIAlertController(title: "New Photo", message: nil, preferredStyle: .ActionSheet)
        
        actionSheet.addAction(UIAlertAction(title: "Camera", style: .Default, handler: { action in
            self.showCamera()
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Album", style: .Default, handler: { action in
            self.showAlbum()
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .Cancel, handler: nil))
        
        self.presentViewController(actionSheet, animated: true, completion: nil)
    }
    
    func showCamera() {
        let cameraPicker = UIImagePickerController()
        cameraPicker.delegate = self
        cameraPicker.sourceType = .Camera
        
        presentViewController(cameraPicker, animated: true, completion: nil)
    }
    
    func showAlbum() {
        let cameraPicker = UIImagePickerController()
        cameraPicker.delegate = self
        cameraPicker.sourceType = .PhotoLibrary
        
        presentViewController(cameraPicker, animated: true, completion: nil)
    }
    
    // MARK: UIImagePickerControllerDelegate
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        dismissViewControllerAnimated(true, completion: nil)
        if let image = info[UIImagePickerControllerOriginalImage] as? UIImage {
            imageView.image = image
            oriImage = image
        }
    }
    
    func imagePickerControllerDidCancel(picker: UIImagePickerController) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    // MARK: Filter Menu
    @IBAction func onFilter(sender: UIButton) {
        sliderView.hidden = true
        LongPress.enabled = true
        if (sender.selected) {
            hideSecondaryMenu()
            sender.selected = false
        } else {
            showSecondaryMenu()
            sender.selected = true
        }
    }
    
    func showSecondaryMenu() {
        view.addSubview(secondaryMenu)
        
        let bottomConstraint = secondaryMenu.bottomAnchor.constraintEqualToAnchor(bottomMenu.topAnchor)
        let leftConstraint = secondaryMenu.leftAnchor.constraintEqualToAnchor(view.leftAnchor)
        let rightConstraint = secondaryMenu.rightAnchor.constraintEqualToAnchor(view.rightAnchor)
        
        let heightConstraint = secondaryMenu.heightAnchor.constraintEqualToConstant(44)
        
        NSLayoutConstraint.activateConstraints([bottomConstraint, leftConstraint, rightConstraint, heightConstraint])
        
        view.layoutIfNeeded()
        
        self.secondaryMenu.alpha = 0
        UIView.animateWithDuration(0.4) {
            self.secondaryMenu.alpha = 1.0
        }
    }

    func hideSecondaryMenu() {
        UIView.animateWithDuration(0.4, animations: {
            self.secondaryMenu.alpha = 0
            }) { completed in
                if completed == true {
                    self.secondaryMenu.removeFromSuperview()
                }
        }
    }

}

