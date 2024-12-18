using System.Diagnostics.CodeAnalysis;
using ComputeSharp;

namespace BruteForce;

[ThreadGroupSize(DefaultThreadGroupSizes.X)]
[GeneratedComputeShaderDescriptor]
[RequiresDoublePrecisionSupport]
[SuppressMessage("Interoperability", "CA1416:Validate platform compatibility")]
public readonly partial struct Compute(ReadWriteBuffer<double> buffer, ReadOnlyBuffer<int> intcode) : IComputeShader
{
    public void Execute()
    {
        var start = buffer[ThreadIds.X];
        var a = start;
        var b = 0.0;
        var c = 0.0;
        var ip = 0;
        var curr = 0;
        while (ip < intcode.Length - 1)
        {
            var ins = intcode[ip];
            var op = intcode[ip + 1];
            double combo = op;
            switch (op)
            {
                case 4:
                    combo = a;
                    break;
                case 5:
                    combo = b;
                    break;
                case 6:
                    combo = c;
                    break;
            }

            switch (ins)
            {
                case 0:
                    while (combo-- > 0)
                    {
                        var res = a / 2;
                        var intPart = res - 4294967296.0 * (uint)(res / 4294967296);
                        if ((uint)intPart != (uint)(intPart + 0.5))
                        {
                            res -= 0.5;
                        }

                        a = res;
                    }

                    break;
                case 1:
                {
                    var temp = (uint)(b / 4294967296);
                    b = 4294967296.0 * temp + ((uint)(b - temp * 4294967296.0) ^ op);
                    break;
                }
                case 2:
                    b = (uint)(combo - 4294967296.0 * (uint)(combo / 4294967296)) % 8;
                    break;
                case 3:
                    if (a >= 1)
                    {
                        ip = op;
                        continue;
                    }

                    break;
                case 4:
                {
                    var tempB = (uint)(b / 4294967296);
                    var tempC = (uint)(c / 4294967296);
                    b = 4294967296.0 * (tempB ^ tempC) + ((uint)(b - tempB * 4294967296.0) ^ (uint)(c - tempC * 4294967296.0));
                    break;
                }

                case 5:
                    buffer[curr] = (uint)(combo - 4294967296.0 * (uint)(combo / 4294967296)) % 8;
                    curr++;

                    break;

                case 6:
                    b = a;
                    while (combo-- > 0)
                    {
                        var res = b / 2;
                        var intPart = res - 4294967296.0 * (uint)(res / 4294967296);
                        if ((uint)intPart != (uint)(intPart + 0.5))
                        {
                            res -= 0.5;
                        }

                        b = res;
                    }

                    break;
                case 7:
                    c = a;
                    while (combo-- > 0)
                    {
                        var res = c / 2;
                        var intPart = res - 4294967296.0 * (uint)(res / 4294967296);
                        if ((uint)intPart != (uint)(intPart + 0.5))
                        {
                            res -= 0.5;
                        }

                        c = res;
                    }

                    break;
            }

            ip += 2;
        }

        buffer[curr] = -1;
    }
}