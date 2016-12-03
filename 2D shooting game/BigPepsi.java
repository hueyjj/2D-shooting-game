public class BigPepsi extends Pepsi
{

    public BigPepsi( int x, int y )
    {
        super( x, y );
        health = 20;
        bossSpeed = .45;
        isBoss = true;
        initBigPepsi();
    }


    public void initBigPepsi()
    {
        loadImage( "BigPepsi.gif" );
        setImageDimensions();
    }
}