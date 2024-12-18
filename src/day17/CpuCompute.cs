namespace BruteForce;

public class CpuCompute
{
    public static double[] CalcLong(int[] intcode, long start)
    {
        var buffer = new double[40];
        var a = start;
        var b = 0L;
        var c = 0L;
        var ip = 0;
        var curr = 0;
        while (ip < intcode.Length - 1)
        {
            var ins = intcode[ip];
            var op = intcode[ip + 1];
            long combo;
            switch (op)
            {
                case 4:
                    combo = (long)a;
                    break;
                case 5:
                    combo = (long)b;
                    break;
                case 6:
                    combo = (long)c;
                    break;
                default:
                    combo = op;
                    break;
            }

            switch (ins)
            {
                case 0:
                    while (combo-- > 0)
                    {
                        a /= 2;
                    }

                    break;
                case 1:
                    b = b ^ op;
                    break;
                case 2:
                    b = combo % 8;
                    break;
                case 3:
                    if (a >= 1)
                    {
                        ip = op;
                        continue;
                    }

                    break;
                case 4:
                    b = b ^ c;
                    break;

                case 5:
                    buffer[curr] = combo % 8;
                    curr++;

                    break;

                case 6:
                    b = a;
                    while (combo-- > 0)
                    {
                        b /= 2;
                    }

                    break;
                case 7:
                    c = a;
                    while (combo-- > 0)
                    {
                        c /= 2;
                    }

                    break;
            }

            ip += 2;
        }

        buffer[curr] = -1;
        return buffer;
    }
}